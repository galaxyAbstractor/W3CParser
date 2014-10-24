/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorsRule1 implements Rule<ArrayList<Person>> {
	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Elements wrongEditors = doc.select("dt:contains(Previous Editor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Former Editor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		Elements editors = doc.select(".p-author");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
			Person result = NameParser.parse(editor.text());

			if (editor.select("a").size() != 0 &&
					!editor.select("a").first().attr("href").isEmpty() &&
					!editor.select("a").first().attr("href").contains("@")) {

				result.setWebsite(editor.select("a").first().attr("href"));
			}

			if (result == null) return null;
			editorList.add(result);
		}

		if (editorList.size() == 0) return null;

		return editorList;

	}
}
