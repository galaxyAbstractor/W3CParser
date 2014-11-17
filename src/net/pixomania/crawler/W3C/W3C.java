/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C;

import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule1;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule2;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule3;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule4;
import net.pixomania.crawler.W3C.parser.rules.contributingAuthors.ContributingAuthorsRule1;
import net.pixomania.crawler.W3C.parser.rules.contributors.ContributorsRule1;
import net.pixomania.crawler.W3C.parser.rules.date.DateRule1;
import net.pixomania.crawler.W3C.parser.rules.editors.*;
import net.pixomania.crawler.W3C.parser.rules.previous.PreviousRule1;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule2;
import net.pixomania.crawler.W3C.parser.rules.seriesEditors.SeriesEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.status.*;
import net.pixomania.crawler.W3C.parser.rules.title.TitleRule1;
import net.pixomania.crawler.W3C.parser.rules.title.TitleRule2;
import net.pixomania.crawler.parser.Parser;

import java.util.HashMap;
import java.util.LinkedList;

public class W3C {
	private static final ParserRunnable parser = new ParserRunnable();

	public static ParserRunnable getParser() {
		return parser;
	}

	private final Thread parserThread = new Thread(parser);

	private final static LinkedList<Standard> standards = new LinkedList<>();
	private final static HashMap<String, Parser> parsers = new HashMap<>();

	public static LinkedList<Standard> getStandards() {
		return standards;
	}

	public static HashMap<String, Parser> getParsers() {
		return parsers;
	}

	public W3C() {
		/*Runnable runGUI = () -> {
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
		}*/

		parsers.put("date", new Parser(new DateRule1()));
		parsers.put("title", new Parser(new TitleRule1(), new TitleRule2()));
		parsers.put("status", new Parser(new StatusRule1(), new StatusRule2(), new StatusRule3(), new StatusRule4(),
				new StatusRule5(), new StatusRule6()));
		parsers.put("editors", new Parser(new EditorsRule1(), new EditorsRule2(), new EditorsRule3()));
		parsers.put("previousEditors", new Parser(new PreviousEditorsRule1(), new PreviousEditorsRule2()));
		parsers.put("seriesEditors", new Parser(new SeriesEditorsRule1()));
		parsers.put("authors", new Parser(new AuthorsRule1(), new AuthorsRule2(), new AuthorsRule3(), new AuthorsRule4()));
		parsers.put("contributors", new Parser(new ContributorsRule1()));
		parsers.put("contributingAuthors", new Parser(new ContributingAuthorsRule1()));
		parsers.put("previous", new Parser(new PreviousRule1()));

		/*standards.add(new Standard(new String[]{"MathML"}, "http://www.w3.org/TR/MathML/"));
		standards.add(new Standard(new String[]{"xml-entity-names"}, "http://www.w3.org/TR/xml-entity-names/"));
		standards.add(new Standard(new String[]{"exi-profile"}, "http://www.w3.org/TR/exi-profile/"));
		standards.add(new Standard(new String[]{"emotionml"}, "http://www.w3.org/TR/emotionml/"));
		standards.add(new Standard(new String[]{"xpath-3"}, "http://www.w3.org/TR/xpath-3/"));
		standards.add(new Standard(new String[]{"xpath-datamodel-30"}, "http://www.w3.org/TR/xpath-datamodel-30/"));
		standards.add(new Standard(new String[]{"xquery-30"}, "http://www.w3.org/TR/xquery-30/"));
		standards.add(new Standard(new String[]{"xqueryx-30"}, "http://www.w3.org/TR/xqueryx-30/"));
		standards.add(new Standard(new String[]{"xslt-xquery-serialization-30"}, "http://www.w3.org/TR/xslt-xquery-serialization-30/"));
		standards.add(new Standard(new String[]{"css-namespaces-3", "css3-namespace"}, "http://www.w3.org/TR/css-namespaces-3/"));
		standards.add(new Standard(new String[]{"wai-aria"}, "http://www.w3.org/TR/wai-aria/"));
		standards.add(new Standard(new String[]{"wai-aria-implementation"}, "http://www.w3.org/TR/wai-aria-implementation/"));
		standards.add(new Standard(new String[]{"mediaont-api"}, "http://www.w3.org/TR/mediaont-api-1.0/"));
		standards.add(new Standard(new String[]{"rdf-schema"}, "http://www.w3.org/TR/rdf-schema/"));
		standards.add(new Standard(new String[]{"rdf-syntax-grammar"}, "http://www.w3.org/TR/rdf-syntax-grammar/"));
		standards.add(new Standard(new String[]{"rdf11-concepts"}, "http://www.w3.org/TR/rdf11-concepts/"));
		standards.add(new Standard(new String[]{"turtle"}, "http://www.w3.org/TR/turtle/"));
		standards.add(new Standard(new String[]{"n-quads"}, "http://www.w3.org/TR/n-quads/"));
		standards.add(new Standard(new String[]{"n-triples"}, "http://www.w3.org/TR/n-triples/"));
		standards.add(new Standard(new String[]{"rdf11-mt"}, "http://www.w3.org/TR/rdf11-mt/"));
		standards.add(new Standard(new String[]{"trig"}, "http://www.w3.org/TR/trig/"));
		standards.add(new Standard(new String[]{"exi"}, "http://www.w3.org/TR/exi/"));
		standards.add(new Standard(new String[]{"progress-events"}, "http://www.w3.org/TR/progress-events/"));
		standards.add(new Standard(new String[]{"cors", "access-control"}, "http://www.w3.org/TR/access-control/"));
		standards.add(new Standard(new String[]{"json-ld-api"}, "http://www.w3.org/TR/json-ld-api/"));
		standards.add(new Standard(new String[]{"vocab-data-cube"}, "http://www.w3.org/TR/vocab-data-cube/"));
		standards.add(new Standard(new String[]{"vocab-org"}, "http://www.w3.org/TR/vocab-org/"));
		standards.add(new Standard(new String[]{"json-ld"}, "http://www.w3.org/TR/json-ld/"));
		standards.add(new Standard(new String[]{"performance-timeline"}, "http://www.w3.org/TR/performance-timeline/"));
		standards.add(new Standard(new String[]{"user-timing"}, "http://www.w3.org/TR/user-timing/"));
		standards.add(new Standard(new String[]{"css-style-attr"}, "http://www.w3.org/TR/css-style-attr/"));
		standards.add(new Standard(new String[]{"widgets-apis"}, "http://www.w3.org/TR/widgets-apis/"));
		standards.add(new Standard(new String[]{"page-visibility"}, "http://www.w3.org/TR/page-visibility/"));
		standards.add(new Standard(new String[]{"its20"}, "http://www.w3.org/TR/its20/"));
		standards.add(new Standard(new String[]{"geolocation-API"}, "http://www.w3.org/TR/geolocation-API/"));
		standards.add(new Standard(new String[]{"touch-events"}, "http://www.w3.org/TR/touch-events/"));
		standards.add(new Standard(new String[]{"ttml1", "ttaf1"}, "http://www.w3.org/TR/ttml1/"));
		standards.add(new Standard(new String[]{"rdfa-core"}, "http://www.w3.org/TR/rdfa-core/"));
		standards.add(new Standard(new String[]{"xhtml-rdfa"}, "http://www.w3.org/TR/xhtml-rdfa/"));
		standards.add(new Standard(new String[]{"html-rdfa", "rdfa-in-html"}, "http://www.w3.org/TR/html-rdfa/"));
		standards.add(new Standard(new String[]{"webstorage"}, "http://www.w3.org/TR/webstorage/"));
		standards.add(new Standard(new String[]{"html5"}, "http://www.w3.org/TR/html5/"));
		standards.add(new Standard(new String[]{"prov-dm"}, "http://www.w3.org/TR/prov-dm/"));
		standards.add(new Standard(new String[]{"prov-o"}, "http://www.w3.org/TR/prov-o/"));
		standards.add(new Standard(new String[]{"prov-constraints"}, "http://www.w3.org/TR/prov-constraints/"));
		standards.add(new Standard(new String[]{"prov-n"}, "http://www.w3.org/TR/prov-n/"));
		standards.add(new Standard(new String[]{"widgets-digsig"}, "http://www.w3.org/TR/widgets-digsig/"));
		standards.add(new Standard(new String[]{"xmldsig-core1"}, "http://www.w3.org/TR/xmldsig-core1/"));
		standards.add(new Standard(new String[]{"xmldsig-properties"}, "http://www.w3.org/TR/xmldsig-properties/"));
		standards.add(new Standard(new String[]{"xmlenc-core1"}, "http://www.w3.org/TR/xmlenc-core1/"));
		standards.add(new Standard(new String[]{"role-attribute"}, "http://www.w3.org/TR/role-attribute/"));
		standards.add(new Standard(new String[]{"rdf-sparql-XMLres"}, "http://www.w3.org/TR/rdf-sparql-XMLres/"));
		standards.add(new Standard(new String[]{"sparql11-entailment"}, "http://www.w3.org/TR/sparql11-entailment/"));
		standards.add(new Standard(new String[]{"sparql11-protocol"}, "http://www.w3.org/TR/sparql11-protocol/"));
		standards.add(new Standard(new String[]{"sparql11-query"}, "http://www.w3.org/TR/sparql11-query/"));
		standards.add(new Standard(new String[]{"sparql11-service-description"}, "http://www.w3.org/TR/sparql11-service-description/"));
		standards.add(new Standard(new String[]{"sparql11-update"}, "http://www.w3.org/TR/sparql11-update/"));
		standards.add(new Standard(new String[]{"sparql11-federated-query"}, "http://www.w3.org/TR/sparql11-federated-query/"));
		standards.add(new Standard(new String[]{"sparql11-overview"}, "http://www.w3.org/TR/sparql11-overview/"));
		standards.add(new Standard(new String[]{"sparql11-results-csv-tsv"}, "http://www.w3.org/TR/sparql11-results-csv-tsv/"));
		standards.add(new Standard(new String[]{"sparql11-results-json"}, "http://www.w3.org/TR/sparql11-results-json/"));
		standards.add(new Standard(new String[]{"selectors-api"}, "http://www.w3.org/TR/selectors-api/"));
		standards.add(new Standard(new String[]{"rif-bld"}, "http://www.w3.org/TR/rif-bld/"));
		standards.add(new Standard(new String[]{"rif-core"}, "http://www.w3.org/TR/rif-core/"));
		standards.add(new Standard(new String[]{"rif-rdf-owl"}, "http://www.w3.org/TR/rif-rdf-owl/"));
		standards.add(new Standard(new String[]{"rif-dtb"}, "http://www.w3.org/TR/rif-dtb/"));
		standards.add(new Standard(new String[]{"rif-fld"}, "http://www.w3.org/TR/rif-fld/"));
		standards.add(new Standard(new String[]{"rif-prd"}, "http://www.w3.org/TR/rif-prd/"));
		standards.add(new Standard(new String[]{"navigation-timing"}, "http://www.w3.org/TR/navigation-timing/"));
		standards.add(new Standard(new String[]{"hr-time"}, "http://www.w3.org/TR/hr-time/"));
		standards.add(new Standard(new String[]{"WOFF"}, "http://www.w3.org/TR/WOFF/"));
		standards.add(new Standard(new String[]{"owl2-mapping-to-rdf"}, "http://www.w3.org/TR/owl2-mapping-to-rdf/"));
		standards.add(new Standard(new String[]{"owl2-new-features"}, "http://www.w3.org/TR/owl2-new-features/"));
		standards.add(new Standard(new String[]{"owl2-primer"}, "http://www.w3.org/TR/owl2-primer/"));
		standards.add(new Standard(new String[]{"owl2-profiles"}, "http://www.w3.org/TR/owl2-profiles/"));
		standards.add(new Standard(new String[]{"owl2-quick-reference"}, "http://www.w3.org/TR/owl2-quick-reference/"));
		standards.add(new Standard(new String[]{"owl2-rdf-based-semantics"}, "http://www.w3.org/TR/owl2-rdf-based-semantics/"));
		standards.add(new Standard(new String[]{"owl2-syntax"}, "http://www.w3.org/TR/owl2-syntax/"));
		standards.add(new Standard(new String[]{"owl2-xml-serialization"}, "http://www.w3.org/TR/owl2-xml-serialization/"));
		standards.add(new Standard(new String[]{"owl2-conformance", "owl2-test"}, "http://www.w3.org/TR/owl2-conformance/"));
		standards.add(new Standard(new String[]{"owl2-direct-semantics", "owl2-semantics"}, "http://www.w3.org/TR/owl2-direct-semantics/"));
		standards.add(new Standard(new String[]{"rdf-plain-literal", "rdf-text"}, "http://www.w3.org/TR/rdf-plain-literal/"));
		standards.add(new Standard(new String[]{"owl2-overview"}, "http://www.w3.org/TR/owl2-overview/"));
		standards.add(new Standard(new String[]{"widgets"}, "http://www.w3.org/TR/widgets/"));
		standards.add(new Standard(new String[]{"mmi-arch"}, "http://www.w3.org/TR/mmi-arch/"));
		standards.add(new Standard(new String[]{"r2rml"}, "http://www.w3.org/TR/r2rml/"));
		standards.add(new Standard(new String[]{"rdb-direct-mapping"}, "http://www.w3.org/TR/rdb-direct-mapping/"));
		standards.add(new Standard(new String[]{"media-frags"}, "http://www.w3.org/TR/media-frags/"));
		standards.add(new Standard(new String[]{"css3-mediaqueries"}, "http://www.w3.org/TR/css3-mediaqueries/"));
		standards.add(new Standard(new String[]{"view-mode", "widgets-vmmf"}, "http://www.w3.org/TR/view-mode/"));
		standards.add(new Standard(new String[]{"rdfa-lite"}, "http://www.w3.org/TR/rdfa-lite/"));
		standards.add(new Standard(new String[]{"xmlschema11-1"}, "http://www.w3.org/TR/xmlschema11-1/"));
		standards.add(new Standard(new String[]{"xmlschema11-2"}, "http://www.w3.org/TR/xmlschema11-2/"));
		standards.add(new Standard(new String[]{"soapjms"}, "http://www.w3.org/TR/soapjms/"));
		standards.add(new Standard(new String[]{"mediaont-10"}, "http://www.w3.org/TR/mediaont-10/"));
		standards.add(new Standard(new String[]{"widgets-access"}, "http://www.w3.org/TR/widgets-access/"));
		standards.add(new Standard(new String[]{"ws-enumeration"}, "http://www.w3.org/TR/ws-enumeration/"));
*/
		//standards.add(new Standard(new String[]{"ws-eventing"}, "http://www.w3.org/TR/ws-eventing/"));
		//standards.add(new Standard(new String[]{"ws-fragment"}, "http://www.w3.org/TR/ws-fragment/"));
		//standards.add(new Standard(new String[]{"ws-metadata-exchange"}, "http://www.w3.org/TR/ws-metadata-exchange/"));
		//standards.add(new Standard(new String[]{"ws-transfer"}, "http://www.w3.org/TR/ws-transfer/"));
		//standards.add(new Standard(new String[]{"ws-event-descriptions"}, "http://www.w3.org/TR/ws-event-descriptions/"));
		//standards.add(new Standard(new String[]{"ws-soap-assertions"}, "http://www.w3.org/TR/ws-soap-assertions/"));
		//standards.add(new Standard(new String[]{"css3-selectors"}, "http://www.w3.org/TR/css3-selectors/"));
		standards.add(new Standard(new String[]{"InkML"}, "http://www.w3.org/TR/InkML/"));


		SpecificEditorsRule2 spE2 = new SpecificEditorsRule2();
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/xmlschema11-1/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2012/PR-xmlschema11-1-20120119/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2011/CR-xmlschema11-1-20110721/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-xmlschema11-1-20091203/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/CR-xmlschema11-1-20090430/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/xmlschema11-2/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2012/PR-xmlschema11-2-20120119/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2011/CR-xmlschema11-2-20110721/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-xmlschema11-2-20091203/", spE2);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/CR-xmlschema11-2-20090430/", spE2);

		SpecificEditorsRule3 spE3 = new SpecificEditorsRule3();
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-xmlschema11-1-20090130/", spE3);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2008/WD-xmlschema11-1-20080620/", spE3);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2007/WD-xmlschema11-1-20070830/", spE3);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2009/WD-xmlschema11-2-20090130/", spE3);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2008/WD-xmlschema11-2-20080620/", spE3);
		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/2007/WD-xmlschema11-2-20070830/", spE3);

		parserThread.start();
	}
}
