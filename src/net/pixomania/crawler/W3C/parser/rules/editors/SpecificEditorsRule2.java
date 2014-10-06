/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.gui.W3CGUI;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

// http://www.w3.org/TR/webrtc/
public class SpecificEditorsRule2 implements Rule<ArrayList<String[]>> {
	@Override
	public ArrayList<String[]> run(String url, Document doc) {
		ArrayList<String[]> editors = (ArrayList<String[]>) W3CGUI.getParsers().get("editors").parse(url, doc, true);
		editors.set(3, new String[]{"Anant Narayanan", "Mozilla", "201211xx"});
		return editors;
	}
}
