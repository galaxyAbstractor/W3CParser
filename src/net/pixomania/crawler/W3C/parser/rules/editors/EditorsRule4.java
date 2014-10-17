/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.parser.rules.Rule;
import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorsRule4 implements Rule<ArrayList<String[]>> {
	@Override
	public ArrayList<String[]> run(String url, Document doc) {
		ArrayList<String[]> editorList = new ArrayList<>();
		Elements wrongEditors = doc.select("dt:contains(Editor')");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("dt:contains(Editors')");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		wrongEditors = doc.select("#authors ~dd");

		if (wrongEditors.size() != 0) {
			wrongEditors.remove();
		}

		Elements editors = doc.select("dt:contains(Editor) ~ dd");

		if (editors.size() != 0) {
			for (Element editor : editors) {
				String[] ed = new String[3];

				ed[2] = "";

				String editorText = editor.text();

				String[] split = editorText.split(", ");
				ed[0] = split[0].trim().replace(" (deceased)", "");

				String[] copy = new String[split.length-1];
				System.arraycopy(split, 1, copy, 0, split.length-1);
				ed[1] = StringUtils.join(copy, ", ").trim();

				editorList.add(ed);
			}

			if (editorList.size() == 0) return null;

			return editorList;
		} else {
			return null;
		}
	}
}
