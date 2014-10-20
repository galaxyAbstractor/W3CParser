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
