/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.name;

public class RegexRule {
	public String regex;
	public int[] group;

	public RegexRule(String regex, int[] group) {
		this.regex = regex;
		this.group = group;
	}
}
