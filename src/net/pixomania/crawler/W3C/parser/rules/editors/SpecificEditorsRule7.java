/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

public class SpecificEditorsRule7 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> persons = new ArrayList<>();

		Person p1 = new Person();
		p1.setName("Patrick Ion");
		p1.setStandardAffiliation("Mathematical Reviews / American Mathematical Society");
		p1.setEmail("ion@ams.org");
		p1.setFull("Patrick Ion <ion@ams.org> (Mathematical Reviews / American Mathematical Society)");
		persons.add(p1);

		Person p2 = new Person();
		p2.setName("Robert Miner");
		p2.setStandardAffiliation("Geometry Technologies, Inc.");
		p2.setEmail("rminer@geomtech.com");
		p2.setFull("Robert Miner <rminer@geomtech.com> (Geometry Technologies, Inc.)");
		persons.add(p2);

		return persons;
	}
}
