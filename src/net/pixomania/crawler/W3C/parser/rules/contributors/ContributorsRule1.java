/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.contributors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.logger.Log;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ContributorsRule1 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Elements editors = doc.select("dt:contains(Contributor) ~ dd");
		if (editors.size() == 0) return null;

		boolean skip = false;
		for (Element editor : editors) {
			Element prev = editor.previousElementSibling();
			if (prev.tagName().equals("dt")) {
				if ((!prev.text().trim().toLowerCase().startsWith("contributor")
						&& !prev.text().trim().toLowerCase().startsWith("additional contributor"))
				 		|| prev.text().trim().toLowerCase().contains("contributorlabel")
				 		|| prev.text().trim().toLowerCase().contains("contributorlink")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = editor.nextElementSibling();
				if (next != null) {
					if (next.text().trim().toLowerCase().startsWith("contributor")
							|| next.text().trim().toLowerCase().startsWith("additional contributor")) {
						skip = false;
						continue;
					}
				}
				continue;
			}

			String[] splitted = editor.html().split("<br />");
			if (splitted.length < 2) splitted = editor.html().split("<br clear=\"none\" />");

			if (splitted.length < 2) {
				if (editor.text().toLowerCase().startsWith("(in alphabetic")
						|| editor.text().toLowerCase().startsWith("see ackn")
						|| editor.text().toLowerCase().startsWith("see the")
						|| editor.text().toLowerCase().startsWith("see ref")
						|| editor.text().toLowerCase().startsWith("see participants")
						|| editor.text().toLowerCase().contains("note:")
						|| editor.text().toLowerCase().startsWith("there are")
						|| editor.text().toLowerCase().equals("see partipants.")) {
					Log.log("warning", "Spec " + url + " may refer to a different section!");
					continue;
				}
				Person result = NameParser.parse(editor.text());
				if (result == null) return null;

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
								|| split.toLowerCase().startsWith("see ackn")
								|| split.toLowerCase().startsWith("see the")
								|| split.toLowerCase().startsWith("see ref")
								|| split.toLowerCase().startsWith("see participants")
								|| split.toLowerCase().contains("note:")
								|| split.toLowerCase().startsWith("there are")
								|| split.toLowerCase().startsWith("see partipants")) {
							Log.log("warning", "Spec " + url + " may refer to a different section!");
							continue;
						}
						Person result = NameParser.parse(split.replaceAll("\n", ""));
						if (result == null) return null;

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
