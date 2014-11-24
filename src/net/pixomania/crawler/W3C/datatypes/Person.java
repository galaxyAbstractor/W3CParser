/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Person {
	public String name;
	public String currentAffiliation;
	public String currentAffiliationUntil;
	public String standardAffiliation;
	public String standardAffiliationUntil;
	public String viaAffiliation;
	public String email;
	public String workgroup;
	public List<String> websites;
	public String formerAffiliation;
	public String full;
	public int rule;
	public long id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentAffiliation() {
		return currentAffiliation;
	}

	public void setCurrentAffiliation(String currentAffiliation) {
		this.currentAffiliation = currentAffiliation;
	}

	public String getCurrentAffiliationUntil() {
		return currentAffiliationUntil;
	}

	public void setCurrentAffiliationUntil(String currentAffiliationUntil) {
		this.currentAffiliationUntil = currentAffiliationUntil;
	}

	public String getStandardAffiliation() {
		return standardAffiliation;
	}

	public void setStandardAffiliation(String standardAffiliation) {
		this.standardAffiliation = standardAffiliation;
	}

	public String getStandardAffiliationUntil() {
		return standardAffiliationUntil;
	}

	public void setStandardAffiliationUntil(String standardAffiliationUntil) {
		this.standardAffiliationUntil = standardAffiliationUntil;
	}

	public String getViaAffiliation() {
		return viaAffiliation;
	}

	public void setViaAffiliation(String viaAffiliation) {
		this.viaAffiliation = viaAffiliation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkgroup() {
		return workgroup;
	}

	public void setWorkgroup(String workgroup) {
		this.workgroup = workgroup;
	}

	public List<String> getWebsites() {
		return websites;
	}

	public void setWebsites(List<String> websites) {
		this.websites = websites;
	}

	public void addWebsite(String website) {
		if (this.websites == null) this.websites = new ArrayList<>();

		if (!this.websites.contains(website)) this.websites.add(website);
	}

	public String getFormerAffiliation() {
		return formerAffiliation;
	}

	public void setFormerAffiliation(String formerAffiliation) {
		this.formerAffiliation = formerAffiliation;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFull() {
		return full;
	}

	public void setFull(String full) {
		this.full = full;
	}

	public int getRule() {
		return rule;
	}

	public void setRule(int rule) {
		this.rule = rule;
	}
}
