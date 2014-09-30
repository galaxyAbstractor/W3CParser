/*
 * @copyright (c) 2014, Victor Nagy
 * @license BSD - $root/license
 */

package net.pixomania.crawler.parser.rules;

import org.jsoup.nodes.Document;

public interface Rule<T> {
	public T run(String url, Document doc);
}
