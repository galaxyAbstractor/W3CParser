/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.rules.editors;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorsRule2 implements Rule<ArrayList<String[]>> {
	@Override
	public ArrayList<String[]> run(String url, Document doc) {
		ArrayList<String[]> editorList = new ArrayList<>();

		Elements wrongEditors = doc.select("dt:contains(Editor')");

		if (wrongEditors.size() != 0) {
			wrongEditors.get(0).remove();
		}

		Elements editors = doc.select("dt:contains(Editor) ~ dd");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
			String[] ed = new String[3];

			ed[2] = "";

			String editorText = editor.text();

			String re1=".*?";	// Non-greedy match on filler
			String re2="((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?))";	// Month 1
			String re3="(\\s+)";	// White Space 1
			String re4="((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	// Day 1
			String re5="(,)";	// Any Single Character 1
			String re6="(\\s+)";	// White Space 2
			String re7="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";	// Year 1

			Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(editor.text());
			if(m.find()) {
				String date = m.group(6);

				switch (m.group(1)) {
					case "January":
						date += "01";
						break;
					case "February":
						date += "02";
						break;
					case "March":
						date += "03";
						break;
					case "April":
						date += "04";
						break;
					case "May":
						date += "05";
						break;
					case "June":
						date += "06";
						break;
					case "July":
						date += "07";
						break;
					case "August":
						date += "08";
						break;
					case "September":
						date += "09";
						break;
					case "October":
						date += "10";
						break;
					case "November":
						date += "11";
						break;
					case "December":
						date += "12";
						break;
				}

				date += m.group(3);

				ed[2] = date;

				re1=".*?";	// Non-greedy match on filler
				re2="((\\()";	// Any Single Character 1
				re3="((?:[a-z][a-z]+))";	// Word 1
				re4="(\\s+)";	// White Space 1
				re5="((?:Jan(?:uary)?|Feb(?:ruary)?|Mar(?:ch)?|Apr(?:il)?|May|Jun(?:e)?|Jul(?:y)?|Aug(?:ust)?|Sep(?:tember)?|Sept|Oct(?:ober)?|Nov(?:ember)?|Dec(?:ember)?))";	// Month 1
				re6="(\\s+)";	// White Space 2
				re7="((?:(?:[0-2]?\\d{1})|(?:[3][01]{1})))(?![\\d])";	// Day 1
				String re8="(,)";	// Any Single Character 2
				String re9="(\\s+)";	// White Space 3
				String re10="((?:(?:[1]{1}\\d{1}\\d{1}\\d{1})|(?:[2]{1}\\d{3})))(?![\\d])";	// Year 1
				String re11="(\\)))";	// Any Single Character 3

				p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8 + re9 + re10 + re11, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
				m = p.matcher(editorText);

				m.find();
				editorText = editorText.replace(m.group(2) + m.group(3) + m.group(4) +
						m.group(5) + m.group(6) + m.group(7) + m.group(8) + m.group(9) +
						m.group(10) + m.group(11), "");
			}

			String[] split = editorText.split(", ");
			if (split.length < 2) return null;

			ed[0] = split[0];
			ed[1] = split[1];

			editorList.add(ed);
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
