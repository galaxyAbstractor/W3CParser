/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

import net.pixomania.crawler.W3C.datatypes.StandardVersion;

public class LogMessage {
	public String level;
	public String message;
	public StandardVersion standardVersion;

	public LogMessage(String level, String message, StandardVersion standardVersion) {
		this.level = level;
		this.message = message;
		this.standardVersion = standardVersion;
	}
}
