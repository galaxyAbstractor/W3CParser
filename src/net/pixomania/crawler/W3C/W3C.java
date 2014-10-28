/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C;

import javafx.application.Application;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.gui.W3CGUI;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule1;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule2;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule3;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule4;
import net.pixomania.crawler.W3C.parser.rules.contributors.ContributorsRule1;
import net.pixomania.crawler.W3C.parser.rules.date.DateRule1;
import net.pixomania.crawler.W3C.parser.rules.date.DateRule2;
import net.pixomania.crawler.W3C.parser.rules.editors.*;
import net.pixomania.crawler.W3C.parser.rules.previous.PreviousRule1;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule2;
import net.pixomania.crawler.W3C.parser.rules.seriesEditors.SeriesEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.status.*;
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
		parsers.put("status", new Parser(new StatusRule1(), new StatusRule2(), new StatusRule3(), new StatusRule4(),
				new StatusRule5(), new StatusRule6()));
		parsers.put("editors", new Parser(new EditorsRule1(), new EditorsRule2()));
		parsers.put("previousEditors", new Parser(new PreviousEditorsRule1(), new PreviousEditorsRule2()));
		parsers.put("seriesEditors", new Parser(new SeriesEditorsRule1()));
		parsers.put("authors", new Parser(new AuthorsRule1(), new AuthorsRule2(), new AuthorsRule3(), new AuthorsRule4()));
		parsers.put("contributors", new Parser(new ContributorsRule1()));
		parsers.put("previous", new Parser(new PreviousRule1()));

		//standards.add(new Standard(new String[]{"MathML"}, "http://www.w3.org/TR/MathML/"));
		//standards.add(new Standard(new String[]{"xml-entity-names"}, "http://www.w3.org/TR/xml-entity-names/"));
		//standards.add(new Standard(new String[]{"exi-profile"}, "http://www.w3.org/TR/exi-profile/"));
		//standards.add(new Standard(new String[]{"emotionml"}, "http://www.w3.org/TR/emotionml/"));
		//standards.add(new Standard(new String[]{"xpath-3"}, "http://www.w3.org/TR/xpath-3/"));
		//standards.add(new Standard(new String[]{"xpath-datamodel-30"}, "http://www.w3.org/TR/xpath-datamodel-30/"));
		//standards.add(new Standard(new String[]{"xquery-30"}, "http://www.w3.org/TR/xquery-30/"));
		//standards.add(new Standard(new String[]{"xqueryx-30"}, "http://www.w3.org/TR/xqueryx-30/"));
		//standards.add(new Standard(new String[]{"xslt-xquery-serialization-30"}, "http://www.w3.org/TR/xslt-xquery-serialization-30/"));
		//standards.add(new Standard(new String[]{"css-namespaces-3"}, "http://www.w3.org/TR/css-namespaces-3/"));
		//standards.add(new Standard(new String[]{"wai-aria"}, "http://www.w3.org/TR/2009/WD-wai-aria"));
		//standards.add(new Standard(new String[]{"wai-aria-implementation"}, "http://www.w3.org/TR/wai-aria-implementation/"));
		//standards.add(new Standard(new String[]{"mediaont-api"}, "http://www.w3.org/TR/2011/WD-mediaont-api-1.0/"));
		//standards.add(new Standard(new String[]{"rdf-schema"}, "http://www.w3.org/TR/rdf-schema/"));
		//standards.add(new Standard(new String[]{"rdf-syntax-grammar"}, "http://www.w3.org/TR/rdf-syntax-grammar/"));
		//standards.add(new Standard(new String[]{"rdf11-concepts"}, "http://www.w3.org/TR/rdf11-concepts/"));
		//standards.add(new Standard(new String[]{"turtle"}, "http://www.w3.org/TR/turtle/"));
		//standards.add(new Standard(new String[]{"n-quads"}, "http://www.w3.org/TR/n-quads/"));
		//standards.add(new Standard(new String[]{"n-triples"}, "http://www.w3.org/TR/n-triples/"));
		//standards.add(new Standard(new String[]{"rdf11-mt"}, "http://www.w3.org/TR/rdf11-mt/"));
		//standards.add(new Standard(new String[]{"trig"}, "http://www.w3.org/TR/trig/"));
		//standards.add(new Standard(new String[]{"exi"}, "http://www.w3.org/TR/exi/"));
		//standards.add(new Standard(new String[]{"progress-events"}, "http://www.w3.org/TR/progress-events/"));
		//standards.add(new Standard(new String[]{"cors", "access-control"}, "http://www.w3.org/TR/2008/WD-access-control-20080912/"));
		//standards.add(new Standard(new String[]{"json-ld-api"}, "http://www.w3.org/TR/json-ld-api/"));
		//standards.add(new Standard(new String[]{"vocab-data-cube"}, "http://www.w3.org/TR/vocab-data-cube/"));


//		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-WebSimpleDB-20090929/", new SpecificEditorsRule1());

		parserThread.start();
	}
}
