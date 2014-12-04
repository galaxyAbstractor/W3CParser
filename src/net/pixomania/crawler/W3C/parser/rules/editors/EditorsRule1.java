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
	public String name = this.getClass().getSimpleName();

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

		wrongEditors = doc.select("dt:contains(Author) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		Elements editors = doc.select(".p-author");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
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
		}

		if (editorList.size() == 0) return null;

		return editorList;

	}
}
