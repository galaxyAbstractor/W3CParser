/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.authors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class SpecificAuthorsRule3 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> persons = new ArrayList<>();

		Person p1 = new Person();
		p1.setName("David Singer");
		p1.setEmail("singer@almaden.ibm.com");
		p1.setFull("David Singer <singer@almaden.ibm.com>");
		persons.add(p1);

		Person p2 = new Person();
		p2.setName("Jim Miller");
		p2.setEmail("jmiller@w3.org");
		p2.setFull("Jim Miller <jmiller@w3.org>");
		persons.add(p2);

		Person p3 = new Person();
		p3.setName("Paul Resnick");
		p3.setEmail("presnick@research.att.com");
		p3.setFull("Paul Resnick <presnick@research.att.com>");
		persons.add(p3);

		return persons;
	}
}
