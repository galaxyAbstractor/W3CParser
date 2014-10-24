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
import net.pixomania.crawler.W3C.parser.rules.previous.PreviousRule2;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule2;
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

		// We need to wait for GUI to initialize before going on
		synchronized (parsers) {
			try {
				parsers.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		parsers.put("date", new Parser(new DateRule1(), new DateRule2()));
		parsers.put("title", new Parser(new TitleRule1(), new TitleRule2()));
		parsers.put("status", new Parser(new StatusRule1(), new StatusRule2(), new StatusRule3()));
		parsers.put("editors", new Parser(new EditorsRule1(), new EditorsRule2()));
		parsers.put("previousEditors", new Parser(new PreviousEditorsRule1(), new PreviousEditorsRule2()));
		parsers.put("previous", new Parser(new PreviousRule1(), new PreviousRule2()));

		//standards.add(new Standard("MathML", "http://www.w3.org/TR/MathML/"));
		//standards.add(new Standard("xml-entity-names", "http://www.w3.org/TR/xml-entity-names/"));
		//standards.add(new Standard("exi-profile", "http://www.w3.org/TR/exi-profile/"));
		//standards.add(new Standard("emotionml", "http://www.w3.org/TR/emotionml/"));
		//standards.add(new Standard("xpath-3", "http://www.w3.org/TR/xpath-3/"));
		//standards.add(new Standard("xpath-datamodel-30", "http://www.w3.org/TR/xpath-datamodel-30/"));
		//standards.add(new Standard("xquery-30", "http://www.w3.org/TR/xquery-30/"));
		//standards.add(new Standard("xqueryx-30", "http://www.w3.org/TR/xqueryx-30/"));
		//standards.add(new Standard("xslt-xquery-serialization-30", "http://www.w3.org/TR/xslt-xquery-serialization-30/"));
		//standards.add(new Standard("css-namespaces-3", "http://www.w3.org/TR/css-namespaces-3/"));
		standards.add(new Standard("wai-aria", "http://www.w3.org/TR/2009/WD-wai-aria-20090224/"));

//		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-WebSimpleDB-20090929/", new SpecificEditorsRule1());

		parserThread.start();
	}
}
