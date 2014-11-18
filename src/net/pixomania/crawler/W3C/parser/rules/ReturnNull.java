/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

public class ReturnNull implements Rule<String> {
	@Override
	public String run(String url, Document doc) {
		return null;
	}
}
