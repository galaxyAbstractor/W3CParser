/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorsRule2 implements Rule<ArrayList<ArrayList<String>>> {
	@Override
	public ArrayList<ArrayList<String>> run(String url, Document doc) {
		ArrayList<ArrayList<String>> editorList = new ArrayList<>();

		Elements wrongEditors = doc.select("dt:contains(Editor')");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Principal Authors:) ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Editors')");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		Elements editors = doc.select("dt:contains(Editor) ~ dd");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
			ArrayList<String> result = NameParser.parse(editor.text());

			if (result == null) return null;
			editorList.add(result);
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
