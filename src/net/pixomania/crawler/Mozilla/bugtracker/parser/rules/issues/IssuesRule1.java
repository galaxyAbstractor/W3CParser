/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.Mozilla.bugtracker.parser.rules.issues;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IssuesRule1 implements Rule<Integer> {
	@Override
	public Integer run(String url, Document doc) {
		Elements resultCount = doc.select(".bz_result_count");
		Pattern p = Pattern.compile("(\\d+)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(resultCount.get(0).text());
		if (!m.find()) return 0;

		return Integer.parseInt(m.group(1));
	}
}
