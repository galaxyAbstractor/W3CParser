/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.status;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatusRule6 implements Rule<String> {
	public String name = this.getClass().getSimpleName();

	@Override
	public String run(String url, Document doc) {
		Elements status = doc.select("h2");

		if (status.size() != 0) {
			Pattern p = Pattern.compile("(W3C ([^\\d]+) (.*))", Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(status.get(1).text());

			if(!m.find()) {
				m = p.matcher(status.get(0).text());
				if(!m.find()) return null;
			}

			return m.group(2);
		} else {
			return null;
		}

	}
}
