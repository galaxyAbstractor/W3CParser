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

public class PreviousRule1 implements Rule<ArrayList<String>> {

	@Override
	public ArrayList<String> run(String url, Document doc) {
		ArrayList<String> urls = new ArrayList<>();

		Elements wrongLinks = doc.select("dt:contains(Previous editors) ~dd");

		if (wrongLinks.size() != 0) {
			wrongLinks.remove();
		}

		wrongLinks = doc.select("dt:contains(Latest recommendation) ~dd");

		if (wrongLinks.size() != 0) {
			wrongLinks.remove();
		}

		wrongLinks = doc.select("dt:contains(Editors) ~dd");

		if (wrongLinks.size() != 0) {
			wrongLinks.remove();
		}

		wrongLinks = doc.select("dt:contains(Previous recommendation) ~dd");

		if (wrongLinks.size() != 0) {
			wrongLinks.remove();
		}

		Elements dt = doc.select("dt:contains(previous) ~ dd");

		if (dt.size() == 0) return null;

		for (Element d : dt) {
			if (d.text().contains(" ")) return null; // Space is a no no
			urls.add(d.text());
		}

		if (urls.size() == 0) return null;

		return urls;
	}
}
