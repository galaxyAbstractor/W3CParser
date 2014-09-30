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

public class Parser<T> {
	private final ArrayList<Rule<T>> rules = new ArrayList<>();
	private final HashMap<String, Rule<T>> specificRules = new HashMap<>();

	@SafeVarargs
	public Parser(Rule<T>... rules) {
		Collections.addAll(this.rules, rules);
	}

	public void addRule(Rule<T> rule) {
		rules.add(rule);
	}

	@SafeVarargs
	public final void addRules(Rule<T>... rules) {
		Collections.addAll(this.rules, rules);
	}

	public void setRuleOnURL(String url, Rule<T> rule) {
		specificRules.put(url, rule);
	}

	public T parse(String url, Document doc) {
		T result = null;

		Rule<T> specificRule = specificRules.get(url);

		if(specificRule == null) {
			for (Rule<T> rule : rules) {
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
}
