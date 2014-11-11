/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

import net.pixomania.crawler.W3C.datatypes.Standard;

public class LogMessage {
	public String level;
	public String message;
	public Standard standard;

	public LogMessage(String level, String message, Standard standard) {
		this.level = level;
		this.message = message;
		this.standard = standard;
	}
}
