/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.datatypes;

import java.util.ArrayList;

public class Standard {

	private final String name;
	private final String link;
	private final ArrayList<StandardVersion> versions = new ArrayList<>();

	public Standard(String name, String link) {
		this.name = name;
		this.link = link;
	}

	@Override
	public String toString() {
		String output = "";
		for (int i = getVersions().size() - 1; i >= 0; i--) {
			for (String[] editor : getVersions().get(i).getEditors()) {
				output += getVersions().get(i).getTitle() + "\t";
				output += editor[0] + "\t";
				output += editor[1] + "\t";
				output += getVersions().get(i).getDate() + "\t";
				output += getVersions().get(i).getStatus() + "\t";
				output += getVersions().get(i).getLink() + "\t\n";
			}
		}

		return output;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public ArrayList<StandardVersion> getVersions() {
		return versions;
	}
}
