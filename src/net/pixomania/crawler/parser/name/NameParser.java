/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.name;

import net.pixomania.crawler.W3C.datatypes.Person;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NameParser {
	private static final ArrayList<RegexRule> regex = new ArrayList<>();
	private static final ArrayList<String> preproc = new ArrayList<>();

	static {
		// name, current affiliation, current affiliation until, standard affiliation
		// standard affiliation until, via affiliation, email, working group, website
		regex.add(new RegexRule("(([^\\(]+) \\((until (\\d{0,2}\\s?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\s?\\d{0,2},? \\d{4}), while with ([^\\(]+))\\))", new int[]{2, 0, 0, 5, 4, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\((while at (.+?(?=, currently)), currently ([^\\(]+)\\)))", new int[]{2, 5, 0, 4, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+) \\(([^\\)]+)\\)\\s?, ([^<]+) <(http[^>]+)>)", new int[]{2, 0, 0, 4, 0, 0, 0, 3, 5}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\)\\s?, ([^<]+) <([^>]+)>)", new int[]{2, 0, 0, 4, 0, 0, 5, 3, 0}));
		regex.add(new RegexRule("(([^,]+), ([^,]+), via (.+))", new int[]{2, 0, 0, 3, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <(http[^>]+)>)", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 4}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <(.+@{1}.+)>)", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0}));
		regex.add(new RegexRule("(([^,]+) <([^>]+)>)", new int[]{2, 0, 0, 0, 0, 0, 3, 0, 0}));

		regex.add(new RegexRule("(([^,]+), (.+))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), (.+))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0}));

		preproc.add("\\(deceased\\)");
	}

	public static Person parse(String name) {
		for (String re : preproc) {
			name = name.replaceAll(re, "");
		}

		System.out.println(name);

		for (RegexRule re : regex) {
			Pattern p = Pattern.compile(re.regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(name);

			if (m.matches()) {
				Field[] fields = Person.class.getDeclaredFields();
				Person person = new Person();
				for (int i = 0; i < re.group.length; i++){
					try {

						if (re.group[i] != 0) {
							fields[i].set(person, m.group(re.group[i]).trim());
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				return person;
			}
		}

		return null;
	}
}
