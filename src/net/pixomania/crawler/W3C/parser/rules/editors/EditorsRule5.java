/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class EditorsRule5 implements Rule<ArrayList<Person>> {
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
				if (!prev.text().replaceAll(":", "").toLowerCase().equals("editor")
						&& !prev.text().replaceAll(":", "").toLowerCase().equals("editors")
						&& !prev.text().toLowerCase().contains("edition editor")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = editor.nextElementSibling();
				if (next != null) {
					if (next.text().replaceAll(":", "").toLowerCase().equals("editor")
							|| next.text().replaceAll(":", "").toLowerCase().equals("editors")
							|| next.text().replaceAll(":", "").toLowerCase().contains("edition editor")) {
						skip = false;
						continue;
					}
				}
				continue;
			}

			String[] splitted = editor.html().split(" - ");

			for (String split : splitted) {
				if (!split.isEmpty()) {
					if (split.equals("WHATWG:") || split.equals("W3C:")) continue;
					Document newdoc = Jsoup.parse(split.replaceAll("\n", ""));
					Person result = NameParser.parse(newdoc.text());
					if (result == null) return null;

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

			Element next = editor.nextElementSibling();
			if (next != null) if (next.tag().getName().equals("dt")) break;
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
