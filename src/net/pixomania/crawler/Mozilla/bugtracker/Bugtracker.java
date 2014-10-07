/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.Mozilla.bugtracker;

import javafx.application.Application;
import net.pixomania.crawler.Mozilla.bugtracker.gui.BugtrackerGUI;
import net.pixomania.crawler.Mozilla.bugtracker.parser.rules.issues.IssuesRule1;
import net.pixomania.crawler.mapper.PeopleMap;
import net.pixomania.crawler.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class Bugtracker {

	private final static HashMap<String, Parser> parsers = new HashMap<>();
	public static HashMap<String, Parser> getParsers() {
		return parsers;
	}

	private static final ParserRunnable parser = new ParserRunnable();
	private final Thread parserThread = new Thread(parser);

	public Bugtracker() {
		Runnable runGUI = () -> {
			Application.launch(BugtrackerGUI.class);
		};

		Thread thread = new Thread(runGUI);
		thread.start();

		int emailCount = 0;

		for (ArrayList<String> emails : PeopleMap.getMap().values()) {
			emailCount += emails.size();
		}

		BugtrackerGUI.setTotalEmails(emailCount);

		parsers.put("issuecount", new Parser(new IssuesRule1()));

		parserThread.start();

	}
}
