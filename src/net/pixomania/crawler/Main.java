/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler;

import net.pixomania.crawler.Mozilla.Mozilla;
import net.pixomania.crawler.W3C.W3C;
import net.pixomania.crawler.mapper.PeopleMap;

public class Main {

	public static void main(String[] args) {
		PeopleMap.add("Jonas Sicking", "jonas@sicking.cc");
		PeopleMap.add("Anant Arayanan", "anarayanan@mozilla.com");
		PeopleMap.add("Anant Arayanan", "anant@kix.in");
		W3C w3c = new W3C();
		//Mozilla mozilla = new Mozilla();
	}
}
