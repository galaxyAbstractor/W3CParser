/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.Mozilla.bugtracker;


import javafx.application.Platform;
import net.pixomania.crawler.Mozilla.bugtracker.gui.BugtrackerGUI;
import net.pixomania.crawler.W3C.gui.W3CGUI;
import net.pixomania.crawler.mapper.PeopleMap;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;

public class ParserRunnable implements Runnable {

	private final HashMap<String, HashMap<String, Integer>> result = new HashMap<>();
	/*
		Email
		Is assigned to
		Is mentor
		Is CC'd
		Email longdesc - ??? Possibly commented only
		Is QA Contact
		Is Reporter
	 */
	private final String searchUrl = "https://bugzilla.mozilla.org/buglist.cgi?email1=%s&emailassigned_to1=%s&emailbug_mentor1=%s&emailcc1=%s&emaillongdesc1=%s&emailqa_contact1=%s&emailreporter1=%s&emailtype1=exact&query_format=advanced&resolution=---&order=bug_status%%2Cpriority%%2Cassigned_to%%2Cbug_id&limit=0";

	@Override
	public void run() {
		PeopleMap.getMap().forEach((k, v) -> {
			for (String email : v) {
				parse(email, k);
			}
		});

		result.forEach((k, v) -> {
			System.out.println(k + ":");
			v.forEach((k1, v1) -> System.out.println("\t" + k1 + ": " + v1 + " issues"));
		});
	}

	private void parse(String email, String person) {

		Document doc = null;
		try {
			doc = Jsoup.connect(String.format(searchUrl, email, 1, 1, 1, 1, 1, 1)).timeout(20000).get();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (doc == null) throw new NullPointerException();

		HashMap<String, Integer> emailHash = result.get(person);
		if (emailHash == null) emailHash = new HashMap<>();

		// URL is not relevant for this parsing
		emailHash.put(email, (Integer) Bugtracker.getParsers().get("issuecount").parse(null, doc).getResult());

		result.put(person, emailHash);

		Platform.runLater(BugtrackerGUI::updateProgress);
	}
}
