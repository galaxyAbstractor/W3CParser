/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.csv;

import net.pixomania.crawler.datatypes.Standard;
import net.pixomania.crawler.datatypes.StandardVersion;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVExport {
	public static void export(ArrayList<Standard> standards) {
		File CSVFile = new File("exported.csv");

		FileWriter writer = null;
		try {
			if (CSVFile.exists()) CSVFile.delete();
			if (!CSVFile.exists()) CSVFile.createNewFile();
			writer = new FileWriter(new File("exported.csv"));

			writer.append("Standard,Editor,Affiliation,Until,Date,Status,Link\n");

			for (Standard standard : standards) {
				for (StandardVersion standardVersion : standard.getVersions()) {
					for (String[] editor : standardVersion.getEditors()) {
						writer.append(standardVersion.getTitle()).append(",");
						writer.append(editor[0]).append(",");
						writer.append(editor[1]).append(",");
						writer.append(editor[2]).append(",");
						writer.append(standardVersion.getDate()).append(",");
						writer.append(standardVersion.getStatus()).append(",");
						writer.append(standardVersion.getLink()).append("\n");
					}
				}
			}

			writer.append("\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}
}
