/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.status;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class StatusRule1 implements Rule<String> {
	public String name = this.getClass().getSimpleName();

	@Override
	public String run(String url, Document doc) {
		Elements status = doc.select("h2");

		if (status.get(0).select("abbr").size() != 0 &&
				status.get(0).select("time").size() != 0) {
			// The newer versions have abbr and time tags
			status.get(0).select("abbr").get(0).remove();
			status.get(0).select("time").get(0).remove();
			return status.get(0).text().trim();
		} else {
			return null;
		}
	}
}
