/*
 * @copyright (c) 2014, Victor Nagy
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser;

import org.jsoup.nodes.Document;
import net.pixomania.crawler.parser.rules.Rule;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * The Parser class is a holder for specific parser Rules used to gather data
 * from a page. Since the data we need (usually) exists on every page
 * but the HTML structure might be different, we create Rules to
 * do the actual parsing. One Rule might be used on all pages that matches
 * the HTML structure the Rule expect. If the Rule doesn't find anything,
 * next Rule is run until either something is found and if nothing is fun,
 * return null.
 *
 */
public class Parser {
	private final ArrayList<Rule> rules = new ArrayList<>();
	private final HashMap<String, Rule> specificRules = new HashMap<>();

	/**
	 * @param rules Rules that are added when the Parser is initialized
	 */
	public Parser(Rule... rules) {
		Collections.addAll(this.rules, rules);
	}

	/**
	 * Adds many Rules to this parser
	 * @param rules The rules to add, can be multiple arguments
	 */
	public final void addRules(Rule... rules) {
		Collections.addAll(this.rules, rules);
	}

	/**
	 * Sets a Rule on an URL that should override the regular rules
	 * Good for when a single or a few pages needs some special handling
	 * which may mess other pages up.
	 * @param url The URL to override
	 * @param rule The Rule to use
	 */
	public void setRuleOnURL(String url, Rule rule) {
		specificRules.put(url, rule);
	}

	/**
	 * This method calls the parser rules until a rule returns something other than null.
	 * It also checks if there is a specific Rule specified for the given URL and runs that
	 * if found.
	 *
	 * @param url The URL we are parsing, this is checked against specific rules
	 * @param doc The document to be parsed
	 * @return Returns a type based on the generic of the Rule defined in this instance of the parser
	 */
	public Object parse(String url, Document doc) {
		Object result = null;

		Rule specificRule = specificRules.get(url);

		if(specificRule == null) {
			for (Rule rule : rules) {
				result = rule.run(url, doc);
				if (result != null) {
					break;
				}
			}
		} else {
			result = specificRule.run(url, doc);
		}

		return result;
	}

	/**
	 * Overloaded parse method - this enables us to parse a document from within
	 * a specific rule, in case we want to modify a single value but we don't want to
	 * recreate the whole actual parsing to change one thing on the page.
	 *
	 * @param url The URL we are parsing, this is checked against specific rules
	 * @param doc The document to be parsed
	 * @param noSpecific Ignore specific rules - however can be set to whatever, only used for overloading
	 * @return Returns a type based on the generic of the Rule defined in this instance of the parser
	 */
	public Object parse(String url, Document doc, boolean noSpecific) {
		Object result = null;

		for (Rule rule : rules) {
			result = rule.run(url, doc);
			if (result != null) {
				break;
			}
		}

		return result;
	}
}
