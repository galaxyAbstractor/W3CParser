/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorsRule2 implements Rule<ArrayList<Person>> {
	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		// UGH. I should probably figure out a better way to ignore irrelevant DDs and data
		Elements wrongEditors = doc.select("dt:contains(Editor')");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Principal Authors:) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Editors')");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Authors) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Author) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Previous Editor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Former Editor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Series) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Contributor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt ~ dd, dt:contains(Editor)");

		for (int i = 0; i < wrongEditors.size(); i++) {
			if (wrongEditors.get(i).text().contains("Editor:")) break;
			if (wrongEditors.get(i).text().contains("Editors:")) break;
			wrongEditors.get(i).remove();
		}

		Elements editors = doc.select("dl").get(0).select("dt:contains(Editor) ~ dd");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
			String[] splitted = editor.html().split("<br />");
			if (splitted.length < 2) splitted = editor.html().split("<br clear=\"none\" />");

			if (splitted.length < 2) {
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
						Document newdoc = Jsoup.parse(split.replaceAll("\n", ""));
						Person result = NameParser.parse(newdoc.text());
						if (result == null) return null;

						for (int i = 0; i < newdoc.select("a").size(); i++) {
							if (!newdoc.select("a").get(i).attr("href").isEmpty()) {
								if (newdoc.select("a").get(i).attr("href").contains("@")){
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
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
