/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.date;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateRule2 implements Rule<String> {
	@Override
	public String run(String url, Document doc) {
		// Standard used different format where <time> tag was not present
		String re1 = ".*?";
		String re2 = "((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3}))(?:[0]?[1-9]|[1][012])(?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";    // YYYYMMDD 1

		Pattern p = Pattern.compile(re1 + re2, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(url);

		String date;
		if (m.find()) {
			date = m.group(1);
		} else {
			// There was no date in the current URL since the URL
			// is probably the one that leads to the latest version
			// Let's grab the link to the current latest version instead!

			Elements dl = doc.select("dl");
			Elements dd = dl.get(0).select("dd");

			m = p.matcher(dd.get(0).text()); // The first dd contains the permanent link to this version
			if(!m.find()) return null;
			date = m.group(1);
		}

		// Format it so we get the same date format across the board
		return (date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8)).trim();
	}
}
