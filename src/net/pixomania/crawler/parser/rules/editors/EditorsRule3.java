/*
 * @copyright (c) 2014, Victor Nagy
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

public class EditorsRule3 implements Rule<ArrayList<String[]>> {
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
			String[] ed = new String[2];

			String editorText = editor.text();

			String re1=".*?";	// Non-greedy match on filler
			String re2="(\\(.*\\))";	// Round Braces 1

			Pattern p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			Matcher m = p.matcher(editorText);

			if (m.find()) {
				ed[1] = m.group(1).substring(1, m.group(1).length()-1);
				editorText = editorText.replace(m.group(1), "");
			} else {
				ed[1] = "Unknown";
			}

			re1=".*?";	// Non-greedy match on filler
			re2="(<[^>]+>)";	// Tag 1

			p = Pattern.compile(re1+re2,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
			m = p.matcher(editorText);

			m.find();

			editorText = editorText.replace(m.group(1), "").trim();

			ed[0] = editorText;

			editorList.add(ed);

		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
