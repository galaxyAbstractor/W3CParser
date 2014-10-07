/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.mapper;

import java.util.ArrayList;
import java.util.HashMap;

public class PeopleMap {
	private static final HashMap<String, ArrayList<String>> map = new HashMap<>();

	public static HashMap<String, ArrayList<String>> getMap() {
		return map;
	}

	public static void add(String person, String email) {
		ArrayList<String> emails = map.get(person);

		if (emails != null) {
			emails.add(email);
		} else {
			emails = new ArrayList<>();
			emails.add(email);
			map.put(person, emails);
		}
	}

	public static boolean personExists(String person) {
		return map.containsKey(person);
	}
}
