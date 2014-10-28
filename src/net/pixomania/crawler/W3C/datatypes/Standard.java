/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.datatypes;

import java.util.ArrayList;

public class Standard {

	private final String[] names;
	private final String link;
	private final ArrayList<StandardVersion> versions = new ArrayList<>();

	public Standard(String names[], String link) {
		this.names = names;
		this.link = link;
	}

	public String[] getNames() {
		return names;
	}

	public String getMainName() {
		return names[0];
	}

	public String getLink() {
		return link;
	}

	public ArrayList<StandardVersion> getVersions() {
		return versions;
	}

	public boolean nameContains(String prevUrl) {
		for (String name : names) {
			if (prevUrl.contains(name)) {
				return true;
			}
		}
		return false;
	}
}
