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

public class EditorsRule2 implements Rule<ArrayList<String[]>> {
	@Override
	public ArrayList<String[]> run(String url, Document doc) {
		ArrayList<String[]> editorList = new ArrayList<>();

		Elements editors = doc.select("dt:contains(Editor) ~ dd");
		if (editors.size() == 0) return null;

		for (Element editor : editors) {
			String[] ed = editor.text().split(", ");
			// Selector returns too much, but we know (hopefully) that the right one
			// only contains name + affiliation
			if (ed.length == 2) {
				editorList.add(ed);
			} else if (ed.length == 3) {
				// On occasions, W3C writes "Andrei Popescu, Google, Inc", making the split 3
				// Let's skip ", Inc" since it doesn't mean much, we only want Google
				editorList.add(new String[]{ed[0], ed[1]});
			}
		}

		if (editorList.size() == 0) return null;

		return editorList;
	}
}
