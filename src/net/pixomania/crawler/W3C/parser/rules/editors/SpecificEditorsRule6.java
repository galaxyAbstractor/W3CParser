/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class SpecificEditorsRule6 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> persons = new ArrayList<>();

		Person p1 = new Person();
		p1.setName("Dave Raggett");
		p1.addWebsite("http://www.w3.org/People/Raggett");
		p1.setStandardAffiliation("W3C");
		p1.setEmail("dsr@w3.org");
		p1.setFull("Dave Raggett <dsr@w3.org>");
		persons.add(p1);

		Person p2 = new Person();
		p2.setName("Arnaud Le Hors");
		p2.addWebsite("http://www.w3.org/People/Arnaud");
		p2.setStandardAffiliation("W3C");
		p2.setEmail("lehors@w3.org");
		p2.setFull("Arnaud Le Hors <lehors@w3.org>");
		persons.add(p2);

		Person p3 = new Person();
		p3.setName("Ian Jacobs");
		p3.addWebsite("http://www.w3.org/People/Jacobs/");
		p3.setStandardAffiliation("W3C");
		p3.setEmail("ij@w3.org");
		p3.setFull("Ian Jacobs <ij@w3.org>");
		persons.add(p3);

		return persons;
	}
}
