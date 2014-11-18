/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

// http://www.w3.org/TR/WD-CSS2-971104/cover.html
public class SpecificEditorsRule4 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> persons = new ArrayList<>();

		Person p1 = new Person();
		p1.setName("Bert Bos");
		p1.addWebsite("http://www.w3.org/People/Bos/");
		p1.setStandardAffiliation("W3C");
		p1.setEmail("bbos@w3.org");
		persons.add(p1);

		Person p2 = new Person();
		p2.setName("Håkon Wium Lie");
		p2.addWebsite("http://www.w3.org/People/howcome/");
		p2.setStandardAffiliation("W3C");
		p2.setEmail("howcome@w3.org");
		persons.add(p2);

		Person p3 = new Person();
		p3.setName("Chris Lilley");
		p3.addWebsite("http://www.w3.org/People/chris/");
		p3.setStandardAffiliation("W3C");
		p3.setEmail("chris@w3.org");
		persons.add(p3);

		Person p4 = new Person();
		p4.setName("Ian Jacobs");
		p4.addWebsite("http://www.w3.org/People/Jacobs/");
		p4.setStandardAffiliation("W3C");
		p4.setEmail("ij@w3.org");
		persons.add(p4);


		return persons;
	}
}
