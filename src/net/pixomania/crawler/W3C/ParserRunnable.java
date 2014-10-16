/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C;

import com.sun.istack.internal.Nullable;
import javafx.application.Platform;
import net.pixomania.crawler.W3C.csv.CSVExport;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.datatypes.StandardVersion;
import net.pixomania.crawler.W3C.gui.W3CGUI;
import net.pixomania.crawler.mapper.PeopleMap;
import net.pixomania.crawler.parser.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * The ParserRunnable class is the startpoint of the parsing.
 * It iterates over each defined Standard instance, calling all the relevant
 * Parser instances (which in turn runs the Rules), and saves the returned data
 * in StandardVersion instances that are added to the Standard instance
 */
public class ParserRunnable implements Runnable {

	private final ArrayList<StandardVersion> urlsCrawled = new ArrayList<>();

	// Sigh
	private final ArrayList<StandardVersion> orphans = new ArrayList<>();

	private boolean orphan = false;
	public void setOrphan(boolean orphan) {
		this.orphan = orphan;
	}

	private boolean wait = true;

	private final HashSet<String> unmappedEditors = new HashSet<>();

	@Override
	public void run() {
		for (Standard standard : W3C.getStandards()) {
			parseVersion(standard.getLink(), standard, null);
		}

		Platform.runLater(() -> {
			if (orphans.size() != 0) {
				W3CGUI.getBrowser().load("data:text/html,Orphans!");
			} else {
				W3CGUI.getBrowser().load("data:text/html,No more standards!");
			}
		});

		Platform.runLater(() -> W3CGUI.redrawInfopanel("Done", null));
		CSVExport.export(W3C.getStandards());
		CSVExport.exportLinkability(W3C.getStandards());

		System.out.println("Unmapped editors");
		unmappedEditors.forEach(System.out::println);
	}

	/**
	 * This parses a single URL (== version).
	 * This is a recursive method. When the version is done parsing, the URLs to the previous versions
	 * are passed back into this method, and the returned StandardVersion is added to the list of
	 * "previous" versions of the "next" standard (creating a kind of linked list between all versions)
	 *
	 * @param url The URL being parsed
	 * @param standard The standard we are currently parsing
	 * @param svnext The "next" StandardVersion, to be added to the current versions "next" list
	 * @return The resulting StandardVersion
	 */
	private StandardVersion parseVersion(String url, Standard standard, @Nullable StandardVersion svnext) {
		System.out.println("PARSING: " + url);
		Platform.runLater(() -> W3CGUI.getBrowser().load(url));

		StandardVersion sv = null;

		// We need to check for orphans here, since we do not want to create a new StandardVersion
		// instance for something we already crawled, since that would destroy the
		// relationships in the next and prev lists of a StandardVersion
		for (int i = 0; i < orphans.size(); i++) {
			if (orphans.get(i).getLink().equals(url)) {
				sv = orphans.remove(i);
				urlsCrawled.add(sv);
				break;
			}
		}

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (doc == null) throw new NullPointerException();

		if(sv == null) {
			sv = new StandardVersion();
		}

		if (svnext != null) {
			sv.getNext().add(svnext);
		}

		sv.setLink(url);

		Result date = W3C.getParsers().get("date").parse(url, doc);
		sv.setDate((String) date.getResult());
		sv.getRules().put("date", date.getRule());

		Result title = W3C.getParsers().get("title").parse(url, doc);
		sv.setTitle((String) title.getResult());
		sv.getRules().put("title", title.getRule());

		Result status = W3C.getParsers().get("status").parse(url, doc);
		sv.setStatus((String) status.getResult());
		sv.getRules().put("status", status.getRule());

		Result ed = W3C.getParsers().get("editors").parse(url, doc);
		ArrayList<String[]> editors = (ArrayList<String[]>) ed.getResult();
		sv.setEditors(editors);
		sv.getRules().put("editors", ed.getRule());

		for (String[] editor : editors) {
			if (!PeopleMap.personExists(editor[0])) {
				unmappedEditors.add(editor[0]);
			}
		}

		ArrayList<String> urls = (ArrayList<String>) W3C.getParsers().get("previous").parse(url, doc).getResult();

		if (!url.contains(standard.getName())) {
			synchronized (this) {
				try {
					Platform.runLater(() -> W3CGUI.confirmDialog(standard.getName(), url));

					this.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(!orphan) {
			urlsCrawled.add(sv);
			standard.getVersions().add(sv);
		} else {
			orphans.add(sv);
		}

		final StandardVersion innerSv = sv;
		Platform.runLater(() -> W3CGUI.redrawInfopanel(standard.getName(), innerSv));

		if(wait) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(!orphan) {

			if (urls != null) {
				for (String prevUrl : urls) {
					// Have we already crawled this link? If so, prev will contain it
					StandardVersion prev = alreadyCrawled(prevUrl);
					if (prev == null) {
						// We have never crawled d.text(), so let's start
						// EXCEPTION are orphans

					/*
						Check if the link has already been crawled
						Orphans are not added to the crawledURL list since we
						don't want orphans to become a dead end
					*/
						StandardVersion isOrphan = isOrphan(prevUrl);

						if (isOrphan == null) {
							// We have never crawled this before - neither is it an orphan
							// let's crawl it if it contains w3.org
							if (prevUrl.contains("w3.org/TR")) {
								sv.getPrev().add(parseVersion(prevUrl, standard, sv));
							}
						} else if (prevUrl.contains(standard.getName()) && prevUrl.contains("w3.org/TR")) {
						/*
						 	This is an orphan, so let's crawl it, but only if it belongs to the correct standard
							For example, web database version 1 and 2 may contain a link to web storage
							version 1. We only want to crawl the web storage version if we are currently working
							on the web storage standard!
						*/

							sv.getPrev().add(parseVersion(prevUrl, standard, sv));
						} else {
							// This is an orphan, but of a different standard. Let's add it as a previous version
							// to the current standard. Then add the next version to the orphan.
							sv.getPrev().add(isOrphan);
							isOrphan.getNext().add(sv);
						}
					} else {
						// We already crawled this link, so let's add it to the lists
						sv.getPrev().add(prev);
						prev.getNext().add(sv);
					}
				}
			}
		}

		orphan = false;
		return sv;
	}

	private StandardVersion alreadyCrawled(String link) {
		for (StandardVersion crawled : urlsCrawled) {
			if (crawled.getLink().equals(link)) {
				return crawled;
			}
		}

		return null;
	}

	private StandardVersion isOrphan(String link) {
		for (StandardVersion crawled : orphans) {
			if (crawled.getLink().equals(link)) {
				return crawled;
			}
		}

		return null;
	}
}
