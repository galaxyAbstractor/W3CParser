/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
	private static File logFile = new File("src/net/pixomania/crawler/log.json");

	public static void createLogFile() {
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();

				FileWriter fileWriter = new FileWriter(logFile);
				fileWriter.write("[");
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			logFile.delete();
			createLogFile();
		}
	}

	public static void appendRow(LogMessage logMessage) {
		try {
			Gson gson = new Gson();

			FileWriter fileWriter = new FileWriter(logFile, true);
			if (logFile.length() < 10) {
				fileWriter.write(gson.toJson(logMessage));
			} else {
				fileWriter.write("," + gson.toJson(logMessage));
			}
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void closeLogFile() {
		try {
			FileWriter fileWriter = new FileWriter(logFile, true);
			fileWriter.write("]");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
