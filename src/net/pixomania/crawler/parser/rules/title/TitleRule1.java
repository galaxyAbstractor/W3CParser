/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.rules.title;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TitleRule1 implements Rule<String> {
	@Override
	public String run(String url, Document doc) {
		Elements title = doc.select("#title");
		if (title.size() != 0) {
			return title.get(0).text().trim();
		} else {
			return null;
		}
	}
}
