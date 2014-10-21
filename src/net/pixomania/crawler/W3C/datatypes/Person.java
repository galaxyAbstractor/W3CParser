/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.datatypes;

public class Person {
	public String name;
	public String currentAffiliation;
	public String currentAffiliationUntil;
	public String standardAffiliation;
	public String standardAffiliationUntil;
	public String viaAffiliation;
	public String email;

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
}
