/*
 * @copyright (c) 2014, Victor Nagy
 * @license BSD - $root/license
 */

package net.pixomania.crawler.datatypes;

import net.pixomania.crawler.parser.rules.Rule;

public class SpecificRule {
	private String url;
	private Rule rule;

	public SpecificRule(String url, Rule rule) {
		this.url = url;
		this.rule = rule;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}
}
