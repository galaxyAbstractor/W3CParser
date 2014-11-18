/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.status;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class SpecificStatusRule1 implements Rule<String> {
	public String name = this.getClass().getSimpleName();

	@Override
	public String run(String url, Document doc) {
		if (url.toLowerCase().contains("wd-")) return "Working Draft";
		if (url.toLowerCase().contains("pr-")) return "Proposed Recommendation";
		if (url.toLowerCase().contains("cr-")) return "Candidate Recommendation";
		if (url.toLowerCase().contains("rec-")) return "Recommendation";

		return null;
	}
}
