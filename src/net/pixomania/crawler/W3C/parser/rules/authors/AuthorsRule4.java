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

		Elements editors = doc.select(".head dt:contains(Author) ~ dd:has(a)");
		if (editors.size() == 0) return null;

		boolean skip = false;
		for (Element editor : editors) {
			Element prev = editor.previousElementSibling();
			if (prev.tagName().equals("dt")) {
				if (!prev.text().replaceAll(":", "").equals("Author")
						&& !prev.text().replaceAll(":", "").equals("Authors")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = editor.nextElementSibling();
				if (next != null) {
					if (next.text().replaceAll(":", "").equals("Author")
							|| next.text().replaceAll(":", "").equals("Authors")) {
						skip = false;
						continue;
					}
				}
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

			Element next = editor.nextElementSibling();
			if (next != null) if (next.tag().getName().equals("dt")) break;
		}

		if (editorList.size() == 0) return null;

		return editorList;

	}
}
