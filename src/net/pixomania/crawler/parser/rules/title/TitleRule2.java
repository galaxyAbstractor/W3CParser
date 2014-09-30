/*
 * @copyright (c) 2014, Victor Nagy
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.rules.title;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TitleRule2 implements Rule<String> {
	@Override
	public String run(String url, Document doc) {
		Elements title = doc.select("h1");
		if (title.size() != 0) {
			return title.get(0).text();
		} else {
			return null;
		}
	}
}
