/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

import com.google.gson.Gson;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.datatypes.StandardVersion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LogWriter {
	private static File logFile = new File("src/net/pixomania/crawler/log.json");

	public static void createLogFile() {
		if (logFile.exists()) {
			logFile.delete();
		}

		try {
			logFile.createNewFile();

			FileWriter fileWriter = new FileWriter(logFile);
			fileWriter.write("[");
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void appendStandard(LogMessage logMessage, Standard standard) {
		net.pixomania.crawler.logger.Standard logStandard = new net.pixomania.crawler.logger.Standard(standard.getNames(), standard.getLink());
		ArrayList<String> svLogs = logStandardVersion(standard);
		logStandard.setId(standard.getId());
		logStandard.setVersions(svLogs);
		logMessage.standard = logStandard;

		try {
			Gson gson = new Gson();

			FileWriter fileWriter = new FileWriter(logFile, true);
			fileWriter.write("," + gson.toJson(logMessage));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
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

	public static ArrayList<String> logStandardVersion(Standard standard) {

		ArrayList<String> svLogs = new ArrayList<>();
		Gson gson = new Gson();
		for (int i = 0; i < standard.getVersions().size(); i += 3) {
			String path = "src/net/pixomania/crawler/log_" + standard.getId() + "_sv_" + i + ".json";
			File svLogFile = new File(path);

			if (svLogFile.exists()) {
				svLogFile.delete();
			}

			try {
				svLogFile.createNewFile();
				FileWriter fileWriter = new FileWriter(svLogFile, true);
				fileWriter.write("[");
				for (int n = 0; n < 3; n++) {
					if ((i + n) >= standard.getVersions().size()) break;
					if (svLogFile.length() < 10) {
						fileWriter.write(gson.toJson(standard.getVersions().get(i + n)));
					} else {
						fileWriter.write("," + gson.toJson(standard.getVersions().get(i + n)));
					}
				}
				fileWriter.write("]");
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			svLogs.add(path);
		}

		return svLogs;

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
