/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.authors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class SpecificAuthorsRule1 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> persons = new ArrayList<>();

		Person p1 = new Person();
		p1.setName("Yang-hua Chu");
		p1.addWebsite("http://www.w3.org/TR/REC-DSig-label/#Yang_hua_Chu");
		p1.setFull("Yang-hua Chu");
		persons.add(p1);

		Person p2 = new Person();
		p2.setName("Philip DesAutels");
		p2.addWebsite("http://www.w3.org/TR/REC-DSig-label/#Philip_DesAutels");
		p2.setFull("Philip DesAutels");
		persons.add(p2);

		Person p3 = new Person();
		p3.setName("Brian LaMacchia");
		p3.addWebsite("http://www.w3.org/TR/REC-DSig-label/#Brian_LaMacchia");
		p3.setFull("Brian LaMacchia");
		persons.add(p3);

		Person p4 = new Person();
		p4.setName("Peter Lipp");
		p4.addWebsite("http://www.w3.org/TR/REC-DSig-label/#Peter_Lipp");
		p4.setFull("Peter Lipp");
		persons.add(p4);

		return persons;
	}
}
