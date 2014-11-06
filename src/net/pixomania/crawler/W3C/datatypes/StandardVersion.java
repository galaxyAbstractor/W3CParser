/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.datatypes;

import net.pixomania.crawler.parser.rules.Rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StandardVersion {
	private String title;
	private String date;
	private String status;
	private ArrayList<Person> editors = new ArrayList<>();
	private ArrayList<Person> previousEditors = new ArrayList<>();
	private ArrayList<Person> seriesEditors = new ArrayList<>();
	private ArrayList<Person> authors = new ArrayList<>();
	private ArrayList<Person> contributors = new ArrayList<>();
	private ArrayList<Person> contributingAuthors = new ArrayList<>();
	private String link;

	private long id;

	private ArrayList<StandardVersion> next = new ArrayList<>();
	private List<StandardVersion> prev = new ArrayList<>();

	private HashMap<String, Rule> rules = new HashMap<>();

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

	public ArrayList<Person> getEditors() {
		return editors;
	}

	public void setEditors(ArrayList<Person> editors) {
		this.editors = editors;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public HashMap<String, Rule> getRules() {
		return rules;
	}

	public ArrayList<StandardVersion> getNext() {
		return next;
	}

	public List<StandardVersion> getPrev() {
		return prev;
	}

	public void setPrev(List<StandardVersion> prev) {
		this.prev = prev;
	}

	public ArrayList<Person> getPreviousEditors() {
		return previousEditors;
	}

	public void setPreviousEditors(ArrayList<Person> previousEditors) {
		this.previousEditors = previousEditors;
	}

	public ArrayList<Person> getSeriesEditors() {
		return seriesEditors;
	}

	public void setSeriesEditors(ArrayList<Person> seriesEditors) {
		this.seriesEditors = seriesEditors;
	}

	public ArrayList<Person> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<Person> authors) {
		this.authors = authors;
	}

	public ArrayList<Person> getContributors() {
		return contributors;
	}

	public void setContributors(ArrayList<Person> contributors) {
		this.contributors = contributors;
	}

	public ArrayList<Person> getContributingAuthors() {
		return contributingAuthors;
	}

	public void setContributingAuthors(ArrayList<Person> contributingAuthors) {
		this.contributingAuthors = contributingAuthors;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
