/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C;

import net.pixomania.crawler.W3C.csv.CSVExport;
import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.parser.rules.ReturnNull;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule1;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule2;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule3;
import net.pixomania.crawler.W3C.parser.rules.authors.AuthorsRule4;
import net.pixomania.crawler.W3C.parser.rules.contributingAuthors.ContributingAuthorsRule1;
import net.pixomania.crawler.W3C.parser.rules.contributors.ContributorsRule1;
import net.pixomania.crawler.W3C.parser.rules.date.DateRule1;
import net.pixomania.crawler.W3C.parser.rules.editorInChief.EditorInChiefRule1;
import net.pixomania.crawler.W3C.parser.rules.editors.*;
import net.pixomania.crawler.W3C.parser.rules.previous.PreviousRule1;
import net.pixomania.crawler.W3C.parser.rules.previous.PreviousRule2;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.previousEditors.PreviousEditorsRule2;
import net.pixomania.crawler.W3C.parser.rules.seriesEditors.SeriesEditorsRule1;
import net.pixomania.crawler.W3C.parser.rules.status.*;
import net.pixomania.crawler.W3C.parser.rules.title.SpecificTitleRule1;
import net.pixomania.crawler.W3C.parser.rules.title.TitleRule1;
import net.pixomania.crawler.W3C.parser.rules.title.TitleRule2;
import net.pixomania.crawler.logger.Log;
import net.pixomania.crawler.parser.Parser;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
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

	public static LinkedList<String> extraLinks = new LinkedList<>();
	public static HashMap<String, String> linkReplacer = new HashMap<>();

	public W3C() {

		//CSVExport.export();
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
				new StatusRule5(), new StatusRule6(), new StatusRule7()));
		parsers.put("editors", new Parser(new EditorsRule1(), new EditorsRule2(), new EditorsRule3(),
				new EditorsRule4(), new EditorsRule5(), new EditorsRule6()));
		parsers.put("previousEditors", new Parser(new PreviousEditorsRule1(), new PreviousEditorsRule2()));
		parsers.put("seriesEditors", new Parser(new SeriesEditorsRule1()));
		parsers.put("authors", new Parser(new AuthorsRule1(), new AuthorsRule2(), new AuthorsRule3(), new AuthorsRule4()));
		parsers.put("contributors", new Parser(new ContributorsRule1()));
		parsers.put("contributingAuthors", new Parser(new ContributingAuthorsRule1()));
		parsers.put("previous", new Parser(new PreviousRule1(), new PreviousRule2()));
		parsers.put("editorInChief", new Parser(new EditorInChiefRule1()));

		//Log.log("warning", "MathML contains principal authors");
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

		standards.add(new Standard(new String[]{"ws-eventing"}, "http://www.w3.org/TR/ws-eventing/"));
		standards.add(new Standard(new String[]{"ws-fragment"}, "http://www.w3.org/TR/ws-fragment/"));
		standards.add(new Standard(new String[]{"ws-metadata-exchange"}, "http://www.w3.org/TR/ws-metadata-exchange/"));
		standards.add(new Standard(new String[]{"ws-transfer"}, "http://www.w3.org/TR/ws-transfer/"));
		standards.add(new Standard(new String[]{"ws-event-descriptions"}, "http://www.w3.org/TR/ws-event-descriptions/"));
		standards.add(new Standard(new String[]{"ws-soap-assertions"}, "http://www.w3.org/TR/ws-soap-assertions/"));
		standards.add(new Standard(new String[]{"css3-selectors"}, "http://www.w3.org/TR/css3-selectors/"));
		standards.add(new Standard(new String[]{"InkML"}, "http://www.w3.org/TR/InkML/"));
		standards.add(new Standard(new String[]{"SVG11"}, "http://www.w3.org/TR/SVG11/"));
		standards.add(new Standard(new String[]{"ccxml"}, "http://www.w3.org/TR/ccxml/"));
		standards.add(new Standard(new String[]{"css3-color"}, "http://www.w3.org/TR/css3-color/"));
		standards.add(new Standard(new String[]{"mathml-for-css"}, "http://www.w3.org/TR/mathml-for-css/"));
		standards.add(new Standard(new String[]{"CSS2"}, "http://www.w3.org/TR/CSS2/"));
		standards.add(new Standard(new String[]{"xpath-fulltext-10", "xpath-full-text-10", "xquery-full-text"}, "http://www.w3.org/TR/xpath-full-text-10/"));
		standards.add(new Standard(new String[]{"xquery-update-10", "xqupdate"}, "http://www.w3.org/TR/xquery-update-10/"));
		standards.add(new Standard(new String[]{"xpath-20", "xpath20"}, "http://www.w3.org/TR/xpath20/"));
		standards.add(new Standard(new String[]{"xquery"}, "http://www.w3.org/TR/xquery/"));
		standards.add(new Standard(new String[]{"xpath-functions", "xquery-operators"}, "http://www.w3.org/TR/xpath-functions/"));
		standards.add(new Standard(new String[]{"xqueryx"}, "http://www.w3.org/TR/xqueryx/"));
		standards.add(new Standard(new String[]{"xpath-datamodel", "query-datamodel"}, "http://www.w3.org/TR/xpath-datamodel/"));
		standards.add(new Standard(new String[]{"xquery-semantics", "query-semantics"}, "http://www.w3.org/TR/xquery-semantics/"));
		standards.add(new Standard(new String[]{"xslt-xquery-serialization"}, "http://www.w3.org/TR/xslt-xquery-serialization/"));
		standards.add(new Standard(new String[]{"mwabp"}, "http://www.w3.org/TR/mwabp/"));
		//TODO standards.add(new Standard(new String[]{"xhtml-basic"}, "http://www.w3.org/TR/xhtml-basic/"));
		//TODO standards.add(new Standard(new String[]{"xhtml11"}, "http://www.w3.org/TR/xhtml11/"));
		//TODO standards.add(new Standard(new String[]{"xhtml-print"}, "http://www.w3.org/TR/xhtml-print/"));
		standards.add(new Standard(new String[]{"xml-stylesheet"}, "http://www.w3.org/TR/xml-stylesheet/"));
		standards.add(new Standard(new String[]{"speech-synthesis11"}, "http://www.w3.org/TR/speech-synthesis11/"));
		standards.add(new Standard(new String[]{"wsc-ui", "wsc-xit"}, "http://www.w3.org/TR/wsc-ui/"));
		//TODO standards.add(new Standard(new String[]{"xhtml-modularization"}, "http://www.w3.org/TR/xhtml-modularization/"));
		standards.add(new Standard(new String[]{"xproc"}, "http://www.w3.org/TR/xproc/"));
		standards.add(new Standard(new String[]{"xlink11"}, "http://www.w3.org/TR/xlink11/"));
		standards.add(new Standard(new String[]{"webcgm21"}, "http://www.w3.org/TR/webcgm21/"));
		//TODO: standards.add(new Standard(new String[]{"DSig-label"}, "http://www.w3.org/TR/REC-DSig-label/"));
		//TODO: standards.add(new Standard(new String[]{"PICS-labels"}, "http://www.w3.org/TR/REC-PICS-labels/"));
		//TODO: standards.add(new Standard(new String[]{"PICS-services"}, "http://www.w3.org/TR/REC-PICS-services/"));
		//TODO: standards.add(new Standard(new String[]{"PICSrules"}, "http://www.w3.org/TR/REC-PICSRules/"));
		standards.add(new Standard(new String[]{"xforms11"}, "http://www.w3.org/TR/xforms11/"));
		standards.add(new Standard(new String[]{"powder-grouping"}, "http://www.w3.org/TR/powder-grouping/"));
		standards.add(new Standard(new String[]{"powder-dr"}, "http://www.w3.org/TR/powder-dr/"));
		standards.add(new Standard(new String[]{"powder-formal"}, "http://www.w3.org/TR/powder-formal/"));
		standards.add(new Standard(new String[]{"skos-reference"}, "http://www.w3.org/TR/skos-reference/"));
		standards.add(new Standard(new String[]{"sml"}, "http://www.w3.org/TR/sml/"));
		standards.add(new Standard(new String[]{"sml-if"}, "http://www.w3.org/TR/sml-if/"));
		standards.add(new Standard(new String[]{"emma"}, "http://www.w3.org/TR/emma/"));
		standards.add(new Standard(new String[]{"xmlbase"}, "http://www.w3.org/TR/xmlbase/"));
		standards.add(new Standard(new String[]{"ElementTraversal"}, "http://www.w3.org/TR/ElementTraversal/"));
		standards.add(new Standard(new String[]{"SVGTiny12", "SVGMobile12"}, "http://www.w3.org/TR/SVGTiny12/"));
		standards.add(new Standard(new String[]{"WCAG20"}, "http://www.w3.org/TR/WCAG20/"));
		standards.add(new Standard(new String[]{"mobileOK-basic10-tests"}, "http://www.w3.org/TR/mobileOK-basic10-tests/"));
		standards.add(new Standard(new String[]{"DDR-Simple-API"}, "http://www.w3.org/TR/DDR-Simple-API/"));
		standards.add(new Standard(new String[]{"SMIL3"}, "http://www.w3.org/TR/SMIL3/"));
		standards.add(new Standard(new String[]{"xml"}, "http://www.w3.org/TR/xml/"));
		standards.add(new Standard(new String[]{"pronunciation-lexicon"}, "http://www.w3.org/TR/pronunciation-lexicon/"));
		standards.add(new Standard(new String[]{"mobile-bp"}, "http://www.w3.org/TR/mobile-bp/"));
		standards.add(new Standard(new String[]{"xmldsig-core"}, "http://www.w3.org/TR/xmldsig-core/"));
		standards.add(new Standard(new String[]{"xml-c14n11"}, "http://www.w3.org/TR/xml-c14n11/"));
		standards.add(new Standard(new String[]{"CSS1"}, "http://www.w3.org/TR/REC-CSS1/"));
		standards.add(new Standard(new String[]{"rdf-sparql-query"}, "http://www.w3.org/TR/rdf-sparql-query/"));
		standards.add(new Standard(new String[]{"rdf-sparql-protocol"}, "http://www.w3.org/TR/rdf-sparql-protocol/"));
		standards.add(new Standard(new String[]{"grddl"}, "http://www.w3.org/TR/grddl/"));
		standards.add(new Standard(new String[]{"grddl-tests"}, "http://www.w3.org/TR/grddl-tests/"));
		standards.add(new Standard(new String[]{"ws-policy"}, "http://www.w3.org/TR/ws-policy/"));
		standards.add(new Standard(new String[]{"ws-policy-attach"}, "http://www.w3.org/TR/ws-policy-attach/"));
		standards.add(new Standard(new String[]{"ws-addr-metadata"}, "http://www.w3.org/TR/ws-addr-metadata/"));
		standards.add(new Standard(new String[]{"sawsdl"}, "http://www.w3.org/TR/sawsdl/"));
		standards.add(new Standard(new String[]{"wsdl20"}, "http://www.w3.org/TR/wsdl20/"));
		standards.add(new Standard(new String[]{"wsdl20-primer"}, "http://www.w3.org/TR/wsdl20-primer/"));
		standards.add(new Standard(new String[]{"wsdl20-adjuncts"}, "http://www.w3.org/TR/wsdl20-adjuncts/"));
		standards.add(new Standard(new String[]{"voicexml21"}, "http://www.w3.org/TR/voicexml21/"));
		standards.add(new Standard(new String[]{"soap12-part1"}, "http://www.w3.org/TR/soap12-part1/"));
		standards.add(new Standard(new String[]{"soap12-part0"}, "http://www.w3.org/TR/soap12-part0/"));
		standards.add(new Standard(new String[]{"soap12-part2"}, "http://www.w3.org/TR/soap12-part2/"));
		standards.add(new Standard(new String[]{"soap12-testcollection"}, "http://www.w3.org/TR/soap12-testcollection/"));
		standards.add(new Standard(new String[]{"semantic-interpretation"}, "http://www.w3.org/TR/semantic-interpretation/"));
		standards.add(new Standard(new String[]{"its"}, "http://www.w3.org/TR/its/"));
		standards.add(new Standard(new String[]{"webcgm20"}, "http://www.w3.org/TR/webcgm20/"));
		standards.add(new Standard(new String[]{"xslt20"}, "http://www.w3.org/TR/xslt20/"));
		standards.add(new Standard(new String[]{"xsl11"}, "http://www.w3.org/TR/xsl11/"));
		standards.add(new Standard(new String[]{"xinclude"}, "http://www.w3.org/TR/xinclude/"));
		standards.add(new Standard(new String[]{"xml11"}, "http://www.w3.org/TR/xml11/"));
		standards.add(new Standard(new String[]{"xml-names11"}, "http://www.w3.org/TR/xml-names11/"));
		standards.add(new Standard(new String[]{"ws-addr-core"}, "http://www.w3.org/TR/ws-addr-core/"));
		standards.add(new Standard(new String[]{"ws-addr-soap"}, "http://www.w3.org/TR/ws-addr-soap/"));
		standards.add(new Standard(new String[]{"smil2"}, "http://www.w3.org/TR/SMIL2/"));
		standards.add(new Standard(new String[]{"xml-id"}, "http://www.w3.org/TR/xml-id/"));
		standards.add(new Standard(new String[]{"qaframe-spec"}, "http://www.w3.org/TR/qaframe-spec/"));
		standards.add(new Standard(new String[]{"xkms2-bindings"}, "http://www.w3.org/TR/xkms2-bindings/"));
		standards.add(new Standard(new String[]{"xkms2"}, "http://www.w3.org/TR/xkms2/"));
		standards.add(new Standard(new String[]{"charmod"}, "http://www.w3.org/TR/charmod/"));
		standards.add(new Standard(new String[]{"soap12-mtom"}, "http://www.w3.org/TR/soap12-mtom/"));*/
		//standards.add(new Standard(new String[]{"soap12-rep"}, "http://www.w3.org/TR/soap12-rep/"));
		//standards.add(new Standard(new String[]{"xop10"}, "http://www.w3.org/TR/xop10/"));
		//standards.add(new Standard(new String[]{"xmlschema-1"}, "http://www.w3.org/TR/xmlschema-1/"));
		//standards.add(new Standard(new String[]{"xmlschema-2"}, "http://www.w3.org/TR/xmlschema-2/"));
		//standards.add(new Standard(new String[]{"xmlschema-0"}, "http://www.w3.org/TR/xmlschema-0/"));
		//standards.add(new Standard(new String[]{"speech-synthesis"}, "http://www.w3.org/TR/speech-synthesis/"));
		//standards.add(new Standard(new String[]{"DOM-Level-3-LS"}, "http://www.w3.org/TR/DOM-Level-3-LS/"));
		//TODO standards.add(new Standard(new String[]{"DOM-Level-3-Core"}, "http://www.w3.org/TR/DOM-Level-3-Core/"));
		//standards.add(new Standard(new String[]{"speech-grammar", "grammar-spec"}, "http://www.w3.org/TR/speech-grammar/"));
		//standards.add(new Standard(new String[]{"voicexml20"}, "http://www.w3.org/TR/voicexml20/"));
		//standards.add(new Standard(new String[]{"rdf-mt"}, "http://www.w3.org/TR/2004/REC-rdf-mt-20040210/"));
		//standards.add(new Standard(new String[]{"rdf-testcases"}, "http://www.w3.org/TR/2004/REC-rdf-testcases-20040210/"));
		//standards.add(new Standard(new String[]{"owl-features"}, "http://www.w3.org/TR/owl-features/"));
		//standards.add(new Standard(new String[]{"owl-guide"}, "http://www.w3.org/TR/owl-guide/"));
		//standards.add(new Standard(new String[]{"owl-ref"}, "http://www.w3.org/TR/owl-ref/"));
		//standards.add(new Standard(new String[]{"owl-semantics", "owl-absyn"}, "http://www.w3.org/TR/owl-semantics/"));
		//standards.add(new Standard(new String[]{"owl-test"}, "http://www.w3.org/TR/owl-test/"));
		//standards.add(new Standard(new String[]{"rdf-concepts"}, "http://www.w3.org/TR/2004/REC-rdf-concepts-20040210/"));
		//standards.add(new Standard(new String[]{"rdf-primer"}, "http://www.w3.org/TR/2004/REC-rdf-primer-20040210/"));
		//standards.add(new Standard(new String[]{"webont-req"}, "http://www.w3.org/TR/webont-req/"));
		//standards.add(new Standard(new String[]{"xml-infoset"}, "http://www.w3.org/TR/xml-infoset/"));
		//standards.add(new Standard(new String[]{"DOM-Level-3-Val"}, "http://www.w3.org/TR/DOM-Level-3-Val/"));
		//standards.add(new Standard(new String[]{"CCPP-struct-vocab", "CCPP-struct", "CCPP-vocab"}, "http://www.w3.org/TR/CCPP-struct-vocab/"));
		//standards.add(new Standard(new String[]{"PNG"}, "http://www.w3.org/TR/PNG/"));
		//Log.log("warning", "MathML2 contains principal authors");
		//standards.add(new Standard(new String[]{"MathML2"}, "http://www.w3.org/TR/MathML2/"));
		//standards.add(new Standard(new String[]{"xptr-element"}, "http://www.w3.org/TR/xptr-element/"));
		//standards.add(new Standard(new String[]{"xptr-framework"}, "http://www.w3.org/TR/xptr-framework/"));
		//standards.add(new Standard(new String[]{"xptr-xmlns"}, "http://www.w3.org/TR/xptr-xmlns/"));
		//standards.add(new Standard(new String[]{"SVGMobile"}, "http://www.w3.org/TR/SVGMobile/"));
		//TODO: standards.add(new Standard(new String[]{"DOM-Level-2-HTML"}, "http://www.w3.org/TR/DOM-Level-2-HTML/"));
		//standards.add(new Standard(new String[]{"UAAG10"}, "http://www.w3.org/TR/UAAG10/"));
		//standards.add(new Standard(new String[]{"xmlenc-decrypt"}, "http://www.w3.org/TR/xmlenc-decrypt"));
		standards.add(new Standard(new String[]{"xmldsig-filter2"}, "http://www.w3.org/TR/xmldsig-filter2/"));

		parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/xmlschema11-1/",
				"http://www.w3.org/TR/2012/PR-xmlschema11-1-20120119/",
				"http://www.w3.org/TR/2011/CR-xmlschema11-1-20110721/",
				"http://www.w3.org/TR/2009/WD-xmlschema11-1-20091203/",
				"http://www.w3.org/TR/2009/CR-xmlschema11-1-20090430/",
				"http://www.w3.org/TR/xmlschema11-2/",
				"http://www.w3.org/TR/2012/PR-xmlschema11-2-20120119/",
				"http://www.w3.org/TR/2011/CR-xmlschema11-2-20110721/",
				"http://www.w3.org/TR/2009/WD-xmlschema11-2-20091203/",
				"http://www.w3.org/TR/2009/CR-xmlschema11-2-20090430/"}, new SpecificEditorsRule2());

		parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/2009/WD-xmlschema11-1-20090130/",
				"http://www.w3.org/TR/2008/WD-xmlschema11-1-20080620/",
				"http://www.w3.org/TR/2007/WD-xmlschema11-1-20070830/",
				"http://www.w3.org/TR/2009/WD-xmlschema11-2-20090130/",
				"http://www.w3.org/TR/2008/WD-xmlschema11-2-20080620/",
				"http://www.w3.org/TR/2007/WD-xmlschema11-2-20070830/"}, new SpecificEditorsRule3());

		parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/WD-CSS2-971104",
				"http://www.w3.org/TR/1998/WD-css2-19980128",
				"http://www.w3.org/TR/1998/PR-CSS2-19980324",
				"http://www.w3.org/TR/1998/REC-CSS2-19980512"}, new SpecificEditorsRule4());
		parsers.get("title").setRuleOnURLs(new String[]{"http://www.w3.org/TR/WD-CSS2-971104",
				"http://www.w3.org/TR/1998/WD-css2-19980128",
				"http://www.w3.org/TR/1998/PR-CSS2-19980324",
				"http://www.w3.org/TR/1998/REC-CSS2-19980512"}, new SpecificTitleRule1());
		parsers.get("previous").setRuleOnURL("http://www.w3.org/TR/WD-CSS2-971104", new ReturnNull());

		parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/1998/WD-xml-stylesheet-19981001", (url, doc) -> {
			ArrayList<Person> persons = new ArrayList<>();

			Person p1 = new Person();
			p1.setName("James Clark");
			p1.setEmail("jjc@jclark.com");
			p1.setFull("James Clark (jjc@jclark.com)");

			persons.add(p1);
			return persons;
		});

		parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/2005/PR-xkms2-bindings-20050502/",
				"http://www.w3.org/TR/2005/PR-xkms2-20050502/"}, (url, doc) -> {
			ArrayList<Person> persons = new ArrayList<>();

			Person p1 = new Person();
			p1.setName("Phillip Hallam-Baker");
			p1.setStandardAffiliation("VeriSign");
			p1.setEmail("xkms-editor@w3.org");
			p1.setFull("Phillip Hallam-Baker VeriSign");

			Person p2 = new Person();
			p2.setName("Shivaram H. Mysore");
			p2.setEmail("xkms-editor@w3.org");
			p2.setFull("Shivaram H. Mysore");

			persons.add(p1);
			persons.add(p2);
			return persons;
		});

		parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/2004/CR-xkms2-bindings-20040405/",
				"http://www.w3.org/TR/2003/WD-xkms2-bindings-20030418/",
				"http://www.w3.org/TR/2002/WD-xkms2-20020318/",
				"http://www.w3.org/TR/2004/CR-xkms2-20040405/",
				"http://www.w3.org/TR/2003/WD-xkms2-20030418/"}, (url, doc) -> {
			ArrayList<Person> persons = new ArrayList<>();

			Person p1 = new Person();
			p1.setName("Phillip Hallam-Baker");
			p1.setStandardAffiliation("VeriSign");
			p1.setEmail("xkms-editor@w3.org");
			p1.setFull("Phillip Hallam-Baker VeriSign");

			persons.add(p1);
			return persons;
		});

		parsers.get("date").setRuleOnURL("http://www.w3.org/TR/REC-CSS1/", (url, doc) -> "2008-04-11");

		parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/REC-CSS1/",
				"http://www.w3.org/TR/1999/REC-CSS1-19990111",
				"http://www.w3.org/TR/REC-CSS1-961217"}, new SpecificEditorsRule5());

		parsers.get("date").setRuleOnURL("http://www.w3.org/1999/05/06-xmlschema-1/", (url, doc) -> "1999-05-06");
		parsers.get("date").setRuleOnURL("http://www.w3.org/1999/05/06-xmlschema-2/", (url, doc) -> "1999-05-06");
		parsers.get("status").setRuleOnURL("http://www.w3.org/1999/05/06-xmlschema-2/", (url, doc) -> "Working Draft");

		parsers.get("date").setRuleOnURL("http://www.w3.org/TR/1998/WD-DOM-Level-2-19981228", (url, doc) -> "1998-12-28");
		parsers.get("date").setRuleOnURL("http://www.w3.org/TR/1999/WD-DOM-Level-2-19990304", (url, doc) -> "1999-03-04");
		parsers.get("date").setRuleOnURL("http://www.w3.org/TR/1999/WD-DOM-Level-2-19990719", (url, doc) -> "1999-07-19");
		parsers.get("date").setRuleOnURL("http://www.w3.org/TR/1999/WD-DOM-Level-2-19990923", (url, doc) -> "1999-09-23");
		parsers.get("date").setRuleOnURL("http://www.w3.org/TR/1999/WD-DOM-Level-2-19990304", (url, doc) -> "1999-03-23");

		extraLinks.add("http://www.w3.org/1999/06/WD-css3-iccprof-19990623");
		extraLinks.add("http://www.w3.org/1999/06/REC-xml-stylesheet-19990629");
		extraLinks.add("http://www.w3.org/Signature/Drafts/WD-xmldsig-core-20000203/");
		extraLinks.add("http://www.w3.org/Signature/Drafts/WD-xmldsig-core-20000128/");
		// extraLinks.add("http://www.w3.org/Signature/Drafts/WD-xmldsig-core-20000114/"); // This is broken, same version as 20000128
		extraLinks.add("http://www.w3.org/1999/05/06-xmlschema-1/");
		extraLinks.add("http://www.w3.org/1999/05/06-xmlschema-2/");
		extraLinks.add("http://www.w3.org/WAI/UA/WD-WAI-USERAGENT-19991029");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19991022");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19991005");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19991004");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19990827");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19990809");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19990716");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19990709");
		extraLinks.add("http://www.w3.org/WAI/UA/WAI-USERAGENT-19990611");

		// Seriously
		linkReplacer.put("http://www.w3.org/TR/1999/WD-DOM-Level-2-9990719", "http://www.w3.org/TR/1999/WD-DOM-Level-2-19990719");
		linkReplacer.put("http://www.w3.org/TR/1999/WD-DOM-Level-2-9990304", "http://www.w3.org/TR/1999/WD-DOM-Level-2-19990304");

		parserThread.start();
	}
}
