/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.title;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TitleRule1 implements Rule<String> {
	public String name = this.getClass().getSimpleName();

	@Override
	public String run(String url, Document doc) {
		Elements title = doc.select("#title");
		if (title.size() != 0) {
			String t = title.get(0).text().trim();
			return t.isEmpty() ? null : t;
		} else {
			return null;
		}
	}
}
