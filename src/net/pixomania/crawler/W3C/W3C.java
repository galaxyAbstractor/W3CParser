/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C;

import javafx.application.Application;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.gui.W3CGUI;
import net.pixomania.crawler.W3C.parser.rules.date.DateRule1;
import net.pixomania.crawler.W3C.parser.rules.date.DateRule2;
import net.pixomania.crawler.W3C.parser.rules.editors.*;
import net.pixomania.crawler.W3C.parser.rules.previous.PreviousRule1;
import net.pixomania.crawler.W3C.parser.rules.status.StatusRule1;
import net.pixomania.crawler.W3C.parser.rules.status.StatusRule2;
import net.pixomania.crawler.W3C.parser.rules.status.StatusRule3;
import net.pixomania.crawler.W3C.parser.rules.title.TitleRule1;
import net.pixomania.crawler.W3C.parser.rules.title.TitleRule2;
import net.pixomania.crawler.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class W3C {
	private static final ParserRunnable parser = new ParserRunnable();

	public static ParserRunnable getParser() {
		return parser;
	}

	private final Thread parserThread = new Thread(parser);

	private final static ArrayList<Standard> standards = new ArrayList<>();
	private final static HashMap<String, Parser> parsers = new HashMap<>();

	public static ArrayList<Standard> getStandards() {
		return standards;
	}

	public static HashMap<String, Parser> getParsers() {
		return parsers;
	}

	public W3C() {
		Runnable runGUI = () -> {
			Application.launch(W3CGUI.class);
		};

		Thread thread = new Thread(runGUI);
		thread.start();

		parsers.put("date", new Parser(new DateRule1(), new DateRule2()));
		parsers.put("title", new Parser(new TitleRule1(), new TitleRule2()));
		parsers.put("status", new Parser(new StatusRule1(), new StatusRule2(), new StatusRule3()));
		parsers.put("editors", new Parser(new EditorsRule1(), new EditorsRule2(), new EditorsRule3()));
		parsers.put("previous", new Parser(new PreviousRule1()));

		standards.add(new Standard("IndexedDB", "http://www.w3.org/TR/IndexedDB/"));
		standards.add(new Standard("webrtc", "http://www.w3.org/TR/webrtc/"));
		standards.add(new Standard("geolocation-API", "http://www.w3.org/TR/geolocation-API/"));
		standards.add(new Standard("webdatabase", "http://www.w3.org/TR/webdatabase/"));
		standards.add(new Standard("webstorage", "http://www.w3.org/TR/webstorage/"));
		standards.add(new Standard("touch-events", "http://www.w3.org/TR/touch-events/"));
		standards.add(new Standard("selectors-api", "http://www.w3.org/TR/selectors-api/"));
		standards.add(new Standard("html-media-capture", "http://www.w3.org/TR/html-media-capture/"));
		standards.add(new Standard("vibration", "http://www.w3.org/TR/2014/CR-vibration-20140909/"));

		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-WebSimpleDB-20090929/", new SpecificEditorsRule1());
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/webrtc/", new SpecificEditorsRule2());

		parserThread.start();
	}
}
