/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

// http://www.w3.org/TR/2009/WD-WebSimpleDB-20090929/ should return Nikunj Mehta, Oracle Corp
public class SpecificEditorsRule1 implements Rule<ArrayList<String[]>> {
	@Override
	public ArrayList<String[]> run(String url, Document doc) {
		ArrayList<String[]> editors = new ArrayList<>();
		editors.add(new String[]{"Nikunj Mehta", "Oracle Corp", ""});
		return editors;
	}
}
