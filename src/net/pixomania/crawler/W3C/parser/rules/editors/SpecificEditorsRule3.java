/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.W3C;
import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.Result;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class SpecificEditorsRule3 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		Elements dl = doc.select("dl dd dl");
		dl.select("dt").get(0).text("Editors:");
		Result ed = W3C.getParsers().get("editors").parse(url, Jsoup.parse(dl.get(0).html()), false);

		return (ArrayList<Person>) ed.getResult();
	}
}
