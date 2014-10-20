/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorsRule1 implements Rule<ArrayList<ArrayList<String>>> {
	@Override
	public ArrayList<ArrayList<String>> run(String url, Document doc) {
		ArrayList<ArrayList<String>> editorList = new ArrayList<>();

		Elements editors = doc.select(".p-author");
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
