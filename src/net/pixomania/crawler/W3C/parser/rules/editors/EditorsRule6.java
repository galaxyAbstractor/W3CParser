/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.logger.Log;
import net.pixomania.crawler.parser.name.NameParser;
import net.pixomania.crawler.parser.rules.Rule;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class EditorsRule6 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editorList = new ArrayList<>();

		Element editor = null;
		try {
			editor = doc.select(".authlist").get(0).select("p").get(0);
		} catch (IndexOutOfBoundsException e) {
			try {
				editor = doc.select("h4:contains(Editors) ~ p").get(0);
			} catch (IndexOutOfBoundsException e1) {
				return null;
			}
		}

		String[] splitted = editor.html().split("<br />");
		if (splitted.length < 2) splitted = editor.html().split("<br clear=\"none\" />");

		for (String split : splitted) {
				if (!split.isEmpty()) {
					if (split.equals("WHATWG:") || split.equals("W3C:")) continue;
					Document newdoc = Jsoup.parse(split.replaceAll("\n", ""));
					Person result = NameParser.parse(newdoc.text());
					if (result == null) continue;

					for (int i = 0; i < newdoc.select("a").size(); i++) {
						if (!newdoc.select("a").get(i).attr("href").isEmpty()) {
							if (newdoc.select("a").get(i).attr("href").contains("@")){
								result.setEmail(newdoc.select("a").get(i).attr("href").replace("mailto:", ""));
							} else {
								result.addWebsite(newdoc.select("a").get(i).attr("href"));
							}
						}
					}

					editorList.add(result);
				}
			}


		if (editorList.size() == 0) return null;

		return editorList;
	}
}
