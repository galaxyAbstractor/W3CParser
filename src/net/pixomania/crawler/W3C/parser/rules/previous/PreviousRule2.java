/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.previous;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class PreviousRule2 implements Rule<ArrayList<String>> {

	@Override
	public ArrayList<String> run(String url, Document doc) {
		ArrayList<String> urls = new ArrayList<>();
		Elements dt = doc.select("dt:contains(previous) ~ dd");

		if (dt.size() == 0) return null;

		for (Element d : dt) {
			Elements a = d.select("a");

			for (Element link : a) {
				urls.add(link.attr("href"));
			}
		}

		if (urls.size() == 0) return null;

		return urls;
	}
}
