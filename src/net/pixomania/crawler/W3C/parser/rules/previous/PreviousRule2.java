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
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<String> run(String url, Document doc) {
		ArrayList<String> urls = new ArrayList<>();

		Elements dt = doc.select("td:contains(previous) ~ td");

		if (dt.size() == 0) return null;

		boolean skip = false;
		for (Element d : dt) {
			Element prev = d.previousElementSibling();
			if (prev.tagName().equals("td")) {
				if (!prev.text().toLowerCase().startsWith("previous version")
						&& !prev.text().toLowerCase().startsWith("previous versions")) {
					skip = true;
				}
			}

			if (skip) {
				Element next = d.nextElementSibling();
				if (next != null) {
					if (next.text().toLowerCase().startsWith("previous version")
							|| next.text().toLowerCase().startsWith("previous versions")) {
						skip = false;
						continue;
					}
				}
				continue;
			}
			Elements a = d.select("a");

			for (Element link : a) {
				if (link.text().contains("color-coded")) continue;
				urls.add(link.attr("href"));
			}
		}

		if (urls.size() == 0) return null;

		return urls;
	}
}
