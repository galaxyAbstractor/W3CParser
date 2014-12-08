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

public class DateRule1 implements Rule<String> {
	public String name = this.getClass().getSimpleName();

	@Override
	public String run(String url, Document doc) {
		// Standard used different format where <time> tag was not present
		Pattern p = Pattern.compile("((\\d{2,4})(\\d{2})(\\d{2}))", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher m = p.matcher(url);

		String date;
		if (m.find()) {
			date = m.group(1);
		} else {
			// There was no date in the current URL since the URL
			// is probably the one that leads to the latest version
			// Let's grab the link to the current latest version instead!

			Elements dl = doc.select("dl");
			if (dl.size() == 0) return null;
			Elements dd = dl.get(0).select("dd");

			if (dd.size() == 0) return null;
			m = p.matcher(dd.get(0).text()); // The first dd contains the permanent link to this version
			if(!m.find()) return null;
			date = m.group(1);
		}

		// Format it so we get the same date format across the board
		if (date.length() == 8) return (date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8)).trim();
		if (date.length() == 6) return "19" + (date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6)).trim();
		return null;
	}
}
