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

public class SpecificDateRule1 implements Rule<String> {
	public String name = this.getClass().getSimpleName();

	@Override
	public String run(String url, Document doc) {
		return "1997-11-04";
	}
}
