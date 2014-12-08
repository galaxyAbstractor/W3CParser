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
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<String> run(String url, Document doc) {
		ArrayList<String> urls = new ArrayList<>();

		Elements dt = doc.select("dt:contains(previous) ~ dd");

		if (dt.size() == 0) return null;

		boolean skip = false;
		for (Element d : dt) {
			Element prev = d.previousElementSibling();
			if (prev.tagName().equals("dt")) {
				if (!prev.text().toLowerCase().startsWith("previous version")
						&& !prev.text().toLowerCase().startsWith("previous official published version")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = d.nextElementSibling();
				if (next != null) {
					if (next.text().toLowerCase().startsWith("previous version")
							|| next.text().toLowerCase().startsWith("previous official published version")) {
						skip = false;
						continue;
					}
				}
				continue;
			}
			Elements a = d.select("a");

			for (Element link : a) {
				if (link.text().contains("color-coded")) continue;

				if (!url.equals(link.attr("href"))) { // W3C...
					if (link.attr("href").startsWith("/")) {
						urls.add("http://www.w3.org" + link.attr("href"));
					} else if(link.attr("href").startsWith("./")) {
						urls.add("http://www.w3.org/TR" + link.attr("href").substring(1));
					} else {
						urls.add(link.attr("href"));
					}
				} else {
					urls.add(link.text());
				}
			}
		}

		if (urls.size() == 0) return null;

		return urls;
	}
}
