/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.status;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class StatusRule2 implements Rule<String> {
	@Override
	public String run(String url, Document doc) {
		Elements status = doc.select("h2");

		if (status.get(0).select("em").size() != 0) {
			// The time and abbr tags are missing, but the date is
			// surrounded by em
			status.get(0).select("em").get(0).remove();
			return status.get(0).text().replace("W3C", "").trim();
		} else {
			return null;
		}
	}
}
