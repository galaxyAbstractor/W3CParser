/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.logger.Log;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class EditorsRule2 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Elements editors = doc.select("dt:contains(Editor) ~ dd, dt:contains(Edition Editor) ~ dd");
		if (editors.size() == 0) return null;

		boolean skip = false;
		for (Element editor : editors) {
			Element prev = editor.previousElementSibling();
			if (prev.tagName().equals("dt")) {
				if ((!prev.text().trim().toLowerCase().startsWith("editor")
						&& !prev.text().trim().toLowerCase().startsWith("edition editor"))
						|| prev.text().trim().toLowerCase().contains("version")
						|| prev.text().trim().toLowerCase().endsWith("draft:")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = editor.nextElementSibling();
				if (next != null) {
					if (next.text().trim().toLowerCase().startsWith("editor")
							|| next.text().trim().toLowerCase().contains("edition editor")) {
						skip = false;
						continue;
					}
				}
				continue;
			}

			if (StringUtils.countMatches(editor.text(), " - ") > 2) {
				Log.log("warning", "This editor may be a list of editors separated by  - ");
				EditorsRule5 ed5 = new EditorsRule5();

				return ed5.run(url, doc);
			}

			String[] splitted = editor.html().split("<br />|<br clear=\"none\" />");

			if (splitted.length < 2) {
				if (editor.text().toLowerCase().startsWith("(in alphabetic")
						|| editor.text().toLowerCase().startsWith("see acknowl")
						|| editor.text().toLowerCase().startsWith("the w3")
						|| editor.text().toLowerCase().startsWith("(see ac")
						|| editor.text().toLowerCase().startsWith("see participants")
						|| editor.text().toLowerCase().contains("note:")) {
					Log.log("warning", "Spec " + url + " may refer to a different section!");
					continue;
				}
				if (editor.text().equals("WHATWG:") || editor.text().equals("W3C:")) continue;
				Person result = NameParser.parse(editor.text());
				if (result == null) continue;

				for (int i = 0; i < editor.select("a").size(); i++) {
					if (!editor.select("a").get(i).attr("href").isEmpty()) {
						if (editor.select("a").get(i).attr("href").contains("@")){
							result.setEmail(editor.select("a").get(i).attr("href").replace("mailto:", ""));
						} else {
							result.addWebsite(editor.select("a").get(i).attr("href"));
						}
					}
				}

				editorList.add(result);
			} else {
				for (String split : splitted) {
					if (!split.isEmpty()) {
						if (split.toLowerCase().startsWith("(in alphabetic")
								|| split.toLowerCase().startsWith("see acknowl")
								|| split.toLowerCase().startsWith("the w3")
								|| split.toLowerCase().startsWith("(see ac")
								|| split.toLowerCase().startsWith("see participants")
								|| split.toLowerCase().contains("note:")) {
							Log.log("warning", "Spec " + url + " may refer to a different section!");
							continue;
						}
						if (split.equals("WHATWG:") || split.equals("W3C:")) continue;
						Document newdoc = Jsoup.parse(split.replaceAll("\n", ""));
						Person result = NameParser.parse(newdoc.text());
						if (result == null) continue;

						for (int i = 0; i < newdoc.select("a").size(); i++) {
							if (!newdoc.select("a").get(i).attr("href").isEmpty()) {
								if (newdoc.select("a").get(i).attr("href").contains("@")){
									result.setEmail(newdoc.select("a").get(i).attr("href").replace("mailto:", ""));
								} else {
									result.addWebsite(newdoc.select("a").get(i).attr("href"));
								}
							}
						}

						editorList.add(result);
					}
				}
			}

			Element next = editor.nextElementSibling();
			if (next != null) if (next.tag().getName().equals("dt")) break;
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
