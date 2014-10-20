/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.name;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameParser {
	private static final ArrayList<RegexRule> regex = new ArrayList<>();
	private static final ArrayList<String> preproc = new ArrayList<>();

	static {
		// (Patrick Ion), (Mathematical Reviews, American Mathematical Society)
		regex.add(new RegexRule("(([a-z .]+){1}, ([a-z ,.]+))", new int[]{2, 3}));

		preproc.add("\\(deceased\\)");
	}

	public static ArrayList<String> parse(String name) {
		for (String re : preproc) {
			name = name.replaceAll(re, "");
		}

		System.out.println(name);

		ArrayList<String> result = new ArrayList<>();
		for (RegexRule re : regex) {
			Pattern p = Pattern.compile(re.regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(name);

			if (m.matches()) {
				for (int group : re.groups) {
					result.add(m.group(group));
				}
			}
		}

		if (result.size() == 0) return null;

		return result;
	}
}
