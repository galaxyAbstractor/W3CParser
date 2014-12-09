/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.csv;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.W3C.datatypes.Standard;
import net.pixomania.crawler.W3C.datatypes.StandardVersion;
import net.pixomania.crawler.db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVExport {
	private static File CSVFile = new File("exported.csv");
	private static FileWriter writer;

	public static void export() {
		try {
			if (CSVFile.exists()) CSVFile.delete();
			if (!CSVFile.exists()) CSVFile.createNewFile();
			writer = new FileWriter(CSVFile);

			writer.append("standard_title;standard_link;version_title;date;status;versionlink;name;currentAffiliation;currentAffiliationUntil;standardAffiliation;standardAffiliationUntil;viaAffiliation;email;workgroup;websites;formerAffiliation;full;version;role;previous\n");

			SessionFactory sf = HibernateUtil.getSessionFactory();
			Session session = sf.openSession();

			Iterator<Standard> iterator = session.createQuery(
					"from Standard")
					.iterate();

			while(iterator.hasNext()) {
				Standard standard = iterator.next();

				standard.getVersions().forEach((sv) -> handleStandardVersion(standard, sv));
			}

			Iterator<StandardVersion> iterator2 = session.createQuery(
					"from StandardVersion where standard_id is null")
					.iterate();

			while (iterator2.hasNext()) {
				StandardVersion sv = iterator2.next();
					handleStandardVersion(new Standard(), sv);
			}

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done exporting!");
	}

	private static void handleStandardVersion(Standard standard, StandardVersion sv) {
		if (sv.getEditors() != null) {
			sv.getEditors().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "editor"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (sv.getAuthors() != null) {
			sv.getAuthors().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "author"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (sv.getContributors() != null) {
			sv.getContributors().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "contributor"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (sv.getPreviousEditors() != null) {
			sv.getPreviousEditors().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "previous editor"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (sv.getSeriesEditors() != null) {
			sv.getSeriesEditors().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "series editor"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (sv.getContributingAuthors() != null) {
			sv.getContributingAuthors().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "contributing author"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}

		if (sv.getEditorInChief() != null) {
			sv.getEditorInChief().forEach((person) -> {
				try {
					writer.append(personRow(standard, sv, person, "editor in chief"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
	}

	private static String personRow(Standard standard, StandardVersion sv, Person person, String role) {
		StringBuilder sb = new StringBuilder();

		sb.append('"').append(standard.getMainName()).append('"').append(";");
		sb.append('"').append(standard.getLink()).append('"').append(";");
		sb.append('"').append(sv.getTitle()).append('"').append(";");
		sb.append('"').append(sv.getDate()).append('"').append(";");
		sb.append('"').append(sv.getStatus()).append('"').append(";");
		sb.append('"').append(sv.getLink()).append('"').append(";");

		Field[] fields = Person.class.getDeclaredFields();

		for (int i = 0; i < 12; i++) {
			try {
				if (i == 8) {
					if (fields[i].get(person) != null){
						List<String> websites = (List<String>) fields[i].get(person);

						if (websites.size() == 0) {
							sb.append("null").append(";");
						} else {
							sb.append("\"").append("[").append(websites.get(0));
							for (int n = 1; n < websites.size(); n++) {
								sb.append(", ").append(websites.get(n));
							}
							sb.append("]").append("\"").append(";");
						}
					}
				} else {
					if (fields[i].get(person) != null) {
						sb.append('"').append(((String) fields[i].get(person)).replaceAll("\"", "\"\"")).append('"').append(";");
					} else {
						sb.append("null").append(";");

					}
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		sb.append(role).append(";");

		if (sv.getPrev() != null){

			if (sv.getPrev().size() == 0) {
				sb.append("null").append("\n");
			} else {
				sb.append("\"").append("[").append(sv.getPrev().get(0).getLink());
				for (int n = 1; n < sv.getPrev().size(); n++) {
					sb.append(", ").append(sv.getPrev().get(n).getLink());
				}
				sb.append("]").append("\"").append("\n");
			}
		}

		return sb.toString();
	}

	public static void exportLinkability(ArrayList<Standard> standards) {
		File CSVFile = new File("exported_link.csv");

		FileWriter writer = null;
		try {
			if (CSVFile.exists()) CSVFile.delete();
			if (!CSVFile.exists()) CSVFile.createNewFile();
			writer = new FileWriter(CSVFile);

			writer.append("Previous,Current\n");

			for (Standard standard : standards) {
				for (StandardVersion standardVersion : standard.getVersions()) {
					for (StandardVersion prev : standardVersion.getPrev()) {
						writer.append(prev.getLink()).append(",");
						writer.append(standardVersion.getLink()).append("\n");
					}
				}
			}

			writer.append(",\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
