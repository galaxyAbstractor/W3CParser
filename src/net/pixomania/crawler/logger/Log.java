/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.datatypes.StandardVersion;

import java.util.LinkedList;

public class Log {
	private static LinkedList<LogMessage> log = new LinkedList<>();

	public static void log(String message) {
		log("info", message, null);
	}

	public static void log(String level, String message) {
		log(level, message, null);
	}

	public static void log(String level, String message, Standard standard) {
		LogWriter.appendRow(new LogMessage(level, message, standard));
	}
}
