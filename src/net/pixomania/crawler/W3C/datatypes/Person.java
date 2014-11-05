/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.datatypes;

import java.util.ArrayList;

public class Person {
	public String name;
	public String currentAffiliation;
	public String currentAffiliationUntil;
	public String standardAffiliation;
	public String standardAffiliationUntil;
	public String viaAffiliation;
	public String email;
	public String workgroup;
	public ArrayList<String> website;
	public String formerAffiliation;
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

	public ArrayList<String> getWebsite() {
		return website;
	}

	public void setWebsite(ArrayList<String> website) {
		this.website = website;
	}

	public void addWebsite(String website) {
		if (this.website == null) this.website = new ArrayList<>();

		if (!this.website.contains(website)) this.website.add(website);
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
}
