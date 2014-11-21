/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.name;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.logger.Log;

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
		// former affiliation
		regex.add(new RegexRule("(([^\\(]+) \\((until (\\d{0,2}\\s?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\s?\\d{0,2},? \\d{4}),? while [with|at]+ ([^\\(]+))\\))", new int[]{2, 0, 0, 5, 4, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\((while at (.+?(?=, currently)), currently ([^\\(]+)\\)))", new int[]{2, 5, 0, 4, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+ \\([^\\)]+\\)) \\(until (\\d{0,2}\\s?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\s?\\d{0,2},? \\d{4}),? while a[t|n] ([^\\)]+)\\))", new int[]{2, 0, 0, 4, 3, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\((until (\\d{4}) while [with|at]+ ([^\\)]+))\\))", new int[]{2, 0, 0, 5, 4, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(former editor, formerly of ([^\\)]+)\\))", new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 3}));
		regex.add(new RegexRule("(([^,]+), ([^\\(]+) \\(formerly of ([^\\)]+)\\))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 4}));
		regex.add(new RegexRule("(([^\\(]+) \\(formerly of ([^\\)]+)\\))", new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 3}));
		regex.add(new RegexRule("(([^\\(]+) \\(formerly of\\S([^\\)]+)\\), <([^@]+@[^>]+)>)", new int[]{2, 0, 0, 0, 0, 0, 4, 0, 0, 3}));
		regex.add(new RegexRule("(([^,]+), ex ([^<]+) <([^@]+@[^>]+)> \\(version[s]? [^;]+; until (\\d{0,2}\\s?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\s?\\d{0,2},? \\d{4})\\))", new int[]{2, 0, 0, 0, 5, 0, 4, 0, 0, 3}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <([^@]+@[^>]+)> \\(version[s]? [^;]+; until (\\d{0,2}\\s?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\s?\\d{0,2},? \\d{4})\\))", new int[]{2, 0, 0, 3, 5, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <([^@]+@[^>]+)> \\((for )?version[s]? [^\\)]+\\))", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+) \\(([^\\)]+)\\)\\s?, ([^<]+) <(http[^>]+)>)", new int[]{2, 0, 0, 4, 0, 0, 0, 3, 5, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\([^\\s]+ edition\\)\\s?, ([^<]+) <([^>]+)>)", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\)\\s?, ([^<]+) <([^>]+)>)", new int[]{2, 0, 0, 4, 0, 0, 5, 3, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(((.*?) wg)\\), ([^,]+), via (.+))", new int[]{2, 0, 0, 5, 0, 6, 0, 3, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^,]+), via (.+))", new int[]{2, 0, 0, 3, 0, 4, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^,]+), and before at ([^\\)]+)\\) <(.+@{1}.+)>)", new int[]{2, 0, 0, 0, 0, 0, 5, 3, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(((.*?) wg)\\) <(.+@[^>]+)>)", new int[]{2, 0, 0, 0, 0, 0, 4, 3, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\([^\\s]+ edition\\) <(.+@{1}.+)>)", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\) <(.+@{1}.+)>)", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^\\(]+) \\(formerly of ([^\\)]+)\\) <(http[^>]+)>)", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 5, 4}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <(http[^>]+)>)", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 4, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\), <([^@]+@[^>]+)>)", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <(.+@{1}.+)>)", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+) <([^>]+)>)", new int[]{2, 0, 0, 0, 0, 0, 3, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(((.*?) wg)\\),? ([^<]+) <(.+)> - [^\\s]+ edition)", new int[]{2, 0, 0, 5, 0, 0, 0, 3, 6, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(((.*?) wg)\\),? <(.+)> - [^\\s]+ edition)", new int[]{2, 0, 0, 0, 0, 0, 5, 3, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <(.+)> - [^\\s]+ edition)", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 4, 0}));
		regex.add(new RegexRule("(([^,]+) <([^>]+)> \\(.+\\))", new int[]{2, 0, 0, 0, 0, 0, 3, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^\\(]+) \\((Until (\\d{0,2}\\s?(?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?)\\s?\\d{0,2},? \\d{4})\\)))", new int[]{2, 0, 0, 3, 5, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^,]+), ([^@]+@.+))", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\((.+)\\), <([^@]+@.+)>)", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^<]+) <([^@]+@[^>]+)> \\((for )?modularization and DTD\\))", new int[]{2, 0, 0, 3, 0, 0, 4, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^@]+@.+))", new int[]{2, 0, 0, 0, 0, 0, 3, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^\\(]+) \\(now at (.+)\\))", new int[]{2, 4, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), formerly at (.+))", new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 3}));
		regex.add(new RegexRule("(([^\\(]+) \\(until (\\d{4}), while at ([^\\)]+)\\))", new int[]{2, 0, 0, 4, 3, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^\\(]+) \\(until (\\d{4})\\))", new int[]{2, 0, 0, 3, 4, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\,]+), ([^\\(]+) \\(editor\\))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(((.*?) wg)\\), ([^,]+))", new int[]{2, 0, 0, 5, 0, 0, 0, 3, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(((.*?) wg)\\),)", new int[]{2, 0, 0, 0, 0, 0, 0, 3, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^-]+) - version .+)",  new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^—]+)—version .+, (.+))",  new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), ([^\\(]+ \\([^\\)]+\\), [^\\(]+) \\(formerly (at|with) ([^\\)]+)\\))",  new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 5}));

		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), (.+))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^\\(]+) \\(([^\\)]+)\\))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+), (.+))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+),(.+))", new int[]{2, 0, 0, 3, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("([^,]+)", new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0}));
		regex.add(new RegexRule("(([^,]+),)", new int[]{2, 0, 0, 0, 0, 0, 0, 0, 0, 0}));

		preproc.add("\\(deceased\\)");
	}

	public static Person parse(String name) {
		// We can make a guess that names, affiliations etc will not be longer than 200 characters
		// if a specification does have this, it can be tweaked. Ugly hack, I know, but eh
		if (name.length() > 200) return null;

		for (String re : preproc) {
			name = name.replaceAll(re, "");
		}

		for (RegexRule re : regex) {
			Pattern p = Pattern.compile(re.regex, Pattern.CASE_INSENSITIVE);
			Matcher m = p.matcher(name);

			if (m.matches()) {
				System.out.println(name + ": " + regex.indexOf(re));
				Field[] fields = Person.class.getDeclaredFields();
				Person person = new Person();
				for (int i = 0; i < re.group.length; i++) {
					// Website is not a string anymore, so it can't be populated
					// by reflection
					if (i == 8) {
						if (re.group[i] != 0) {
							person.addWebsite(m.group(re.group[i]).trim());
						}
						continue;
					}
					try {

						if (re.group[i] != 0) {
							fields[i].set(person, m.group(re.group[i]).trim());
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}

				person.setFull(name);
				person.setRule(regex.indexOf(re));
				return person;
			}
		}
		Log.log("warning", "Name: " + name + " could not be parsed!");
		return null;
	}
}
