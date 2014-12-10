/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.principalAuthors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.W3C.parser.rules.editors.EditorsRule5;
import net.pixomania.crawler.logger.Log;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class PrincipalAuthorsRule1 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Elements editors = doc.select("dt:contains(Principal Author) ~ dd");
		if (editors.size() == 0) return null;

		boolean skip = false;
		for (Element editor : editors) {
			Element prev = editor.previousElementSibling();
			if (prev.tagName().equals("dt")) {
				if (!prev.text().trim().toLowerCase().startsWith("principal author")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = editor.nextElementSibling();
				if (next != null) {
					if (next.text().trim().toLowerCase().startsWith("principal author")) {
						skip = false;
						continue;
					}
				}
				continue;
			}


			String[] splitted = editor.html().split(",");

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

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
