/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class SpecificEditorsRule5 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> persons = new ArrayList<>();

		Person p1 = new Person();
		p1.setName("Bert Bos");
		p1.addWebsite("http://www.w3.org/People/Bos/");
		p1.setStandardAffiliation("W3C");
		p1.setEmail("bbos@w3.org");
		p1.setFull("Bert Bos <bbos@w3.org>");
		persons.add(p1);

		Person p2 = new Person();
		p2.setName("Håkon Wium Lie");
		p2.addWebsite("http://www.w3.org/People/howcome/");
		p2.setStandardAffiliation("W3C");
		p2.setEmail("howcome@w3.org");
		p2.setFull("Håkon Wium Lie <howcome@w3.org>");
		persons.add(p2);

		return persons;
	}
}
