/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.datatypes;

import java.util.ArrayList;

public class StandardVersion {
	private String title;
	private String date;
	private String status;
	private ArrayList<String[]> editors = new ArrayList<>();
	private String link;

	private final ArrayList<StandardVersion> next = new ArrayList<>();
	private final ArrayList<StandardVersion> prev = new ArrayList<>();

	@Override
	public String toString() {
		String output = "";

		output += "Name: " + getTitle() + "\n";
		output += "Link " + getLink() + "\n";
		output += "Date: " + getDate() + "\n";
		output += "Status: " + getStatus() + "\n";
		output += "Editors: \n";

		for (String[] editor : getEditors()) {
			output += "\t" + editor[0] + ", " + editor[1] + "\n";
		}

		output += "Next: " + getNext().size() + "\n";

		output += "Previous: " + getPrev().size() + "\n";

		return output;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<String[]> getEditors() {
		return editors;
	}

	public void setEditors(ArrayList<String[]> editors) {
		this.editors = editors;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}


	public ArrayList<StandardVersion> getNext() {
		return next;
	}

	public ArrayList<StandardVersion> getPrev() {
		return prev;
	}
}
