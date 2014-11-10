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
	private List<Person> editors = new ArrayList<>();
	private List<Person> previousEditors = new ArrayList<>();
	private List<Person> seriesEditors = new ArrayList<>();
	private List<Person> authors = new ArrayList<>();
	private List<Person> contributors = new ArrayList<>();
	private List<Person> contributingAuthors = new ArrayList<>();
	private String link;

	private long id;

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

	public List<Person> getEditors() {
		return editors;
	}

	public void setEditors(List<Person> editors) {
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

	public List<StandardVersion> getPrev() {
		return prev;
	}

	public void setPrev(List<StandardVersion> prev) {
		this.prev = prev;
	}

	public List<Person> getPreviousEditors() {
		return previousEditors;
	}

	public void setPreviousEditors(List<Person> previousEditors) {
		this.previousEditors = previousEditors;
	}

	public List<Person> getSeriesEditors() {
		return seriesEditors;
	}

	public void setSeriesEditors(List<Person> seriesEditors) {
		this.seriesEditors = seriesEditors;
	}

	public List<Person> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Person> authors) {
		this.authors = authors;
	}

	public List<Person> getContributors() {
		return contributors;
	}

	public void setContributors(List<Person> contributors) {
		this.contributors = contributors;
	}

	public List<Person> getContributingAuthors() {
		return contributingAuthors;
	}

	public void setContributingAuthors(List<Person> contributingAuthors) {
		this.contributingAuthors = contributingAuthors;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
