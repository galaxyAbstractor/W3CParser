/*
 * @copyright (c) 2014, Victor Nagy
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.rules.status;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusRule3 implements Rule<String> {

	@Override
	public String run(String url, Document doc) {
		Elements status = doc.select("h2");

		// There is no em tag, let's remove the date with regex

		String re1 = ".*?";    // Non-greedy match on filler
		String re2 = "\\d+";    // Uninteresting: int
		String re3 = ".*?";    // Non-greedy match on filler
		String re4 = "(\\d+)";    // Integer Number 1
		String re5 = "( )";    // White Space 1
		String re6 = "((?:[a-z][a-z]+))";    // Word 1
		String re7 = "(\\s+)";    // White Space 2
		String re8 = "(\\d+)";    // Integer Number 2

		Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(status.get(0).text());
		if(!m.find()) return null;

		return status.get(0).text().replace("W3C", "").
				replace(m.group(5), "").
				replace(m.group(3), "").
				replace(m.group(1), "");
	}
}
