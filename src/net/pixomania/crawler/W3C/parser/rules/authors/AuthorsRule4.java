/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.authors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class AuthorsRule4 implements Rule<ArrayList<Person>> {
	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

/*		Elements wrongEditors = doc.select("dt:contains(Previous Editor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Former Editor) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Contributors) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Editor) ~ dd, dt:contains(Author)");

		for (int i = 0; i < wrongEditors.size(); i++) {
			if (wrongEditors.get(i).text().contains("Author")) break;
			wrongEditors.get(i).remove();
		}*/

		Elements editors = doc.select(".head dt:contains(Author) ~ dd:has(a)");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
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

			Element next = editor.nextElementSibling();
			if (next.tag().getName().equals("dt")) break;
		}

		if (editorList.size() == 0) return null;

		return editorList;

	}
}
