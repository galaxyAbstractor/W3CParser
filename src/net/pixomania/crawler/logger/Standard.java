/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.logger;

import net.pixomania.crawler.W3C.datatypes.StandardVersion;

import java.util.ArrayList;
import java.util.List;

public class Standard {

	private long id;
	private String[] names;
	private String link;
	private List<String> versions = new ArrayList<>();

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

	public List<String> getVersions() {
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
}
