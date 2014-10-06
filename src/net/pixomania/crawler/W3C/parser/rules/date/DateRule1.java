/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.date;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DateRule1 implements Rule<String> {

	@Override
	public String run(String url, Document doc) {
		Elements time = doc.select("time");
		if (time.size() != 0) {
			return time.get(0).attr("datetime").trim();
		} else {
			return null;
		}
	}
}
