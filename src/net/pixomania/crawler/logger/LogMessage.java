/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

public class LogMessage {
	public String level;
	public String message;
	public Standard standard;

	public LogMessage(String level, String message) {
		this.level = level;
		this.message = message;
	}
}
