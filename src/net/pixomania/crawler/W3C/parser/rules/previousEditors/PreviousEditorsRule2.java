/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.previousEditors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class PreviousEditorsRule2 implements Rule<ArrayList<Person>> {
	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Elements editors = doc.select("dt:contains(Previous Editor) ~ dd");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
			String[] splitted = editor.html().split("<br />");
			if (splitted.length < 2) splitted = editor.html().split("<br clear=\"none\" />");

			if (splitted.length < 2) {
				Person result = NameParser.parse(editor.text());

				if (editor.select("a").size() != 0 &&
					!editor.select("a").first().attr("href").isEmpty() &&
					!editor.select("a").first().attr("href").contains("@")) {

					result.setWebsite(editor.select("a").first().attr("href"));
				}

				if (result == null) return null;
				editorList.add(result);
			} else {
				for (String split : splitted) {
					if (!split.isEmpty()) {
						Person result = NameParser.parse(split.replaceAll("\n", ""));

						if (editor.select("a").size() != 0 &&
								!editor.select("a").first().attr("href").isEmpty() &&
								!editor.select("a").first().attr("href").contains("@")) {

							result.setWebsite(editor.select("a").first().attr("href"));
						}

						if (result == null) return null;
						editorList.add(result);
					}
				}
			}
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
