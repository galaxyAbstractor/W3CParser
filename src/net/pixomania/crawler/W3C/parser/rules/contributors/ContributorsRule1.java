/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.contributors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class ContributorsRule1 implements Rule<ArrayList<Person>> {
	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Elements editors = doc.select("dt:contains(Contributor) ~ dd");
		if (editors.size() == 0) return null;

		boolean skip = false;
		for (Element editor : editors) {
			Element prev = editor.previousElementSibling();
			if (prev.tagName().equals("dt")) {
				if (!prev.text().equals("Contributor:")
						&& !prev.text().equals("Contributors:")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = editor.nextElementSibling();
				if (next != null) {
					if (next.text().equals("Contributor:")
							|| next.text().equals("Contributors:")) {
						skip = false;
						continue;
					}
				}
				continue;
			}

			String[] splitted = editor.html().split("<br />");
			if (splitted.length < 2) splitted = editor.html().split("<br clear=\"none\" />");

			if (splitted.length < 2) {
				if (editor.text().toLowerCase().equals("(in alphabetical order)")
						|| editor.text().toLowerCase().equals("(in alphabetic order)")
						|| editor.text().toLowerCase().equals("see Acknowledgements")
						|| editor.text().toLowerCase().equals("see participants.")
						|| editor.text().toLowerCase().contains("note:")) continue;
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
						if (split.toLowerCase().equals("(in alphabetical order)")
								|| split.toLowerCase().equals("(in alphabetic order)")
								|| split.toLowerCase().equals("see Acknowledgements")
								|| split.toLowerCase().equals("see participants.")
								|| split.toLowerCase().contains("note:")) continue;
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
