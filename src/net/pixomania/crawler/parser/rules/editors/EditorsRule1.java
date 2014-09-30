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

public class EditorsRule1 implements Rule<ArrayList<String[]>> {
	@Override
	public ArrayList<String[]> run(String url, Document doc) {
		ArrayList<String[]> editorList = new ArrayList<>();
		Elements editors = doc.select(".p-author");

		if (editors.size() != 0) {
			for (Element editor : editors) {
				String[] ed = editor.text().split(", ");
				editorList.add(ed);
			}

			return editorList;
		} else {
			return null;
		}
	}
}
