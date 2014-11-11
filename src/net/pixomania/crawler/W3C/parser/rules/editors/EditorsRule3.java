/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class EditorsRule3 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		EditorsRule2 editorsRule2 = new EditorsRule2();

		try {
			return editorsRule2.run(url, Jsoup.parse(doc.select("dl").get(2).outerHtml()));
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}
