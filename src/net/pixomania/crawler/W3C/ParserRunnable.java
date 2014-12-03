/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.datatypes.StandardVersion;
import net.pixomania.crawler.db.HibernateUtil;
import net.pixomania.crawler.logger.Log;
import net.pixomania.crawler.logger.LogWriter;
import net.pixomania.crawler.parser.Result;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The ParserRunnable class is the startpoint of the parsing.
 * It iterates over each defined Standard instance, calling all the relevant
 * Parser instances (which in turn runs the Rules), and saves the returned data
 * in StandardVersion instances that are added to the Standard instance
 */
public class ParserRunnable implements Runnable {

	private boolean wait = false;

	private SessionFactory sf = HibernateUtil.getSessionFactory();
	private Session session = sf.openSession();

	private String prevTitle = "";
	@Override
	public void run() {
		LogWriter.createLogFile();
		LinkedList<Standard> standards = W3C.getStandards();

		Standard standard = standards.pop();
		while (standard != null) {
			Log.log("info", "[i] Started parsing standard " + standard.getMainName());
			System.out.println("STANDARD " + standard.getMainName() + " IS BEING PARSED\n================");
			parseVersion(standard.getLink(), standard);
			session.beginTransaction();
			session.save(standard);
			session.getTransaction().commit();

			Log.log("standard", "[+] New Standard: " + standard.getMainName(), standard);

			if(standard.getVersions().size() < 3) {
				Log.log("warning", "Standard " + standard.getMainName() + " has 2 or less versions");
			}

			standard = null;
			if (standards.size() > 0) standard = standards.pop();
			prevTitle = "";
		}


		Standard orphans = new Standard(new String[]{"orphans"}, "");
		orphans.setVersions(allOrphans());
		Log.log("orphans", "Orphans!", orphans);

		/*Platform.runLater(() -> {
			if (orphans.size() != 0) {
				W3CGUI.getBrowser().load("data:text/html,Orphans!");
			} else {
				W3CGUI.getBrowser().load("data:text/html,No more standards!");
			}
		});

		Platform.runLater(() -> W3CGUI.redrawInfopanel("Done", null));*/
		//CSVExport.export(W3C.getStandards());
		//CSVExport.exportLinkability(W3C.getStandards());

		session.close();
		LogWriter.closeLogFile();

		System.out.println("No more standards!");
	}

	/**
	 * This parses a single URL (== version).
	 * This is a recursive method. When the version is done parsing, the URLs to the previous versions
	 * are passed back into this method, and the returned StandardVersion is added to the list of
	 * "previous" versions of the "next" standard (creating a kind of linked list between all versions)
	 *
	 * @param url The URL being parsed
	 * @param standard The standard we are currently parsing
	 * @return The resulting StandardVersion
	 */
	private StandardVersion parseVersion(String url, Standard standard) {
		Log.log("info", "[i] Parsing version: " + url);
		System.out.println("PARSING: " + url);
		//Platform.runLater(() -> W3CGUI.getBrowser().load(url));
		System.out.println("Standards left:" + W3C.getStandards().size());

		StandardVersion sv = new StandardVersion();

		/*// We need to check for orphans here, since we do not want to create a new StandardVersion
		// instance for something we already crawled, since that would destroy the
		// relationships in the next and prev lists of a StandardVersion
		for (int i = 0; i < orphans.size(); i++) {
			if (orphans.get(i).getLink().equals(url)) {
				sv = orphans.remove(i);
				//urlsCrawled.add(sv);
				break;
			}
		}*/

		Document doc = null;
		try {
			doc = Jsoup.connect(url).get();
		} catch (org.jsoup.HttpStatusException e) {
			Log.log("warning", "Version " + url + " gave error code: " + e.getStatusCode());
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (doc == null) throw new NullPointerException();

		sv.setLink(url);

		Result date = W3C.getParsers().get("date").parse(url, doc);
		sv.setDate((String) date.getResult());
		sv.getRules().put("date", date.getRule());
		if (date.getResult() == null) System.out.println("NO DATE " + url);

		Result title = W3C.getParsers().get("title").parse(url, doc);
		sv.setTitle((String) title.getResult());
		sv.getRules().put("title", title.getRule());
		if (title.getResult() == null) Log.log("error", "Spec " + url + " doesn't seem to have a title");

		if (!prevTitle.isEmpty()) {
			if (!prevTitle.toLowerCase().equals(sv.getTitle().toLowerCase())) Log.log("warning", "Title change: " + prevTitle + " changed to " + sv.getTitle());
		}

		prevTitle = sv.getTitle();

		Result status = W3C.getParsers().get("status").parse(url, doc);
		sv.setStatus((String) status.getResult());
		sv.getRules().put("status", status.getRule());
		if (status.getResult() == null) Log.log("error", "Spec " + url + " doesn't seem to have a status");

		Result prevEd = W3C.getParsers().get("previousEditors").parse(url, doc);
		ArrayList<Person> prevEditors = (ArrayList<Person>) prevEd.getResult();
		sv.setPreviousEditors(prevEditors);
		sv.getRules().put("previousEditors", prevEd.getRule());

		Result seriesEd = W3C.getParsers().get("seriesEditors").parse(url, doc);
		ArrayList<Person> seriesEditors = (ArrayList<Person>) seriesEd.getResult();
		sv.setSeriesEditors(seriesEditors);
		sv.getRules().put("seriesEditors", seriesEd.getRule());

		Result authors = W3C.getParsers().get("authors").parse(url, doc);
		ArrayList<Person> authorsList = (ArrayList<Person>) authors.getResult();
		sv.setAuthors(authorsList);
		sv.getRules().put("authors", authors.getRule());

		Result contributors = W3C.getParsers().get("contributors").parse(url, doc);
		ArrayList<Person> contributorsList = (ArrayList<Person>) contributors.getResult();
		sv.setContributors(contributorsList);
		sv.getRules().put("contributors", contributors.getRule());

		Result contributingAuthors = W3C.getParsers().get("contributingAuthors").parse(url, doc);
		ArrayList<Person> contributingAuthorsList = (ArrayList<Person>) contributingAuthors.getResult();
		sv.setContributingAuthors(contributingAuthorsList);
		sv.getRules().put("contributingAuthors", contributingAuthors.getRule());

		Result ed = W3C.getParsers().get("editors").parse(url, doc);
		ArrayList<Person> editors = (ArrayList<Person>) ed.getResult();
		sv.setEditors(editors);
		sv.getRules().put("editors", ed.getRule());

		Result editorInChief = W3C.getParsers().get("editorInChief").parse(url, doc);
		ArrayList<Person> eic = (ArrayList<Person>) editorInChief.getResult();
		sv.setEditorInChief(eic);
		sv.getRules().put("editorInChief", editorInChief.getRule());

		boolean noPeople = true;
		// while loop is because once we find one list that is not null
		// and contains persons, we do not need to check the rest of the lists
		while (true) {
			if (sv.getEditors() != null) {
				if (sv.getEditors().size() > 0) {
					noPeople = false;
					break;
				}
			}

			if (sv.getAuthors() != null) {
				if (sv.getAuthors().size() > 0) {
					noPeople = false;
					break;
				}
			}

			if (sv.getContributors() != null) {
				if (sv.getContributors().size() > 0) {
					noPeople = false;
					break;
				}
			}

			if (sv.getContributingAuthors() != null) {
				if (sv.getContributingAuthors().size() > 0) {
					noPeople = false;
					break;
				}
			}

			if (sv.getSeriesEditors() != null) {
				if (sv.getSeriesEditors().size() > 0) {
					noPeople = false;
					break;
				}
			}

			if (sv.getPreviousEditors() != null) {
				if (sv.getPreviousEditors().size() > 0) {
					noPeople = false;
					break;
				}
			}

			if (sv.getEditorInChief() != null) {
				if (sv.getEditorInChief().size() > 0) {
					noPeople = false;
					break;
				}
			}
			break;
		}

		if (noPeople) {
			Log.log("error", "No persons were found associated with spec " + url);
		}

		ArrayList<String> urls = (ArrayList<String>) W3C.getParsers().get("previous").parse(url, doc).getResult();

		System.out.println(urls);
		boolean contain = false;
		for (String name : standard.getNames()) {
			if (url.trim().toLowerCase().contains(name.toLowerCase())) {
				contain = true;
				break;
			}
		}

/*		if (!contain) {
			if (wait) {
				*//*synchronized (this) {
					try {
						Platform.runLater(() -> W3CGUI.confirmDialog(standard.getMainName(), url));

						this.wait();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}*//*
			} else {
				orphan = true;
			}
		}*/



		Log.log("info", "Completed parsing " + url);

		if (contain) {
			standard.getVersions().add(sv);
		}

		//final StandardVersion innerSv = sv;
		//Platform.runLater(() -> W3CGUI.redrawInfopanel(standard.getMainName(), innerSv));

		if(wait) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (urls != null && urls.size() > 0) {
			for (String prevUrl : urls) {
				prevUrl = prevUrl.trim(); // ...
				if (W3C.linkReplacer.containsKey(prevUrl)) prevUrl = W3C.linkReplacer.get(prevUrl);

				if (prevUrl.length() < 1) continue;
				if (prevUrl.equals(url)) continue; // W3C...
				if ((!prevUrl.contains("w3.org/TR") || prevUrl.endsWith(".txt")) && !W3C.extraLinks.contains(prevUrl)) continue;

				// Have we already crawled this link? If so, prev will contain it
				StandardVersion prev = alreadyCrawled(prevUrl.trim());
				if (prev == null) {
					// We have never crawled d.text(), so let's start

					// We have never crawled this before
					// let's crawl it if it contains w3.org
					if ((prevUrl.contains("w3.org/TR") && !prevUrl.endsWith(".txt")) || W3C.extraLinks.contains(prevUrl)) {
						StandardVersion nextToCrawl = parseVersion(prevUrl, standard);

						if (nextToCrawl != null) {
							sv.getPrev().add(nextToCrawl);
							session.beginTransaction();
							session.save(sv);
							session.getTransaction().commit();
						}
					}

				} else {
					// We already crawled this link, so let's add it to the lists
					sv.getPrev().add(prev);
					session.beginTransaction();
					session.save(sv);
					session.getTransaction().commit();
				}
			}
		}

		return sv;
	}

	private StandardVersion alreadyCrawled(String link) {
		while (link.endsWith("/")) link = link.substring(0, link.length() - 1); // W3C occasionally has a LOT of /

		URL url = null;
		try {
			url = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String lastPart = url.getFile();

		if (lastPart.lastIndexOf(".") > 0) lastPart = lastPart.substring(0, lastPart.lastIndexOf("."));

		return (StandardVersion) session.createQuery(
				"from StandardVersion where link like :link")
				.setParameter("link", "%" + lastPart + "%")
				.uniqueResult();

	}

	private List<StandardVersion> allOrphans() {
		return (List<StandardVersion>) session.createQuery(
				"from StandardVersion where standard_id is null").list();

	}
}
