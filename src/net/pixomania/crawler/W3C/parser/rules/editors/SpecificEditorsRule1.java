/*
 * @copyright (c) 2014, Victor Nagy, University of Skövde
 * @license BSD - $root/license
 */

package net.pixomania.crawler.W3C.parser.rules.editors;

import net.pixomania.crawler.W3C.datatypes.Person;
import net.pixomania.crawler.parser.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;

// http://www.w3.org/TR/2009/WD-WebSimpleDB-20090929/ should return Nikunj Mehta, Oracle Corp
public class SpecificEditorsRule1 implements Rule<ArrayList<Person>> {
	public String name = this.getClass().getSimpleName();

	@Override
	public ArrayList<Person> run(String url, Document doc) {
		ArrayList<Person> editors = new ArrayList<>();
		Person person = new Person();
		person.setName("Nikunj Mehta");
		person.setStandardAffiliation("Oracle Corp");
		editors.add(person);
		return editors;
	}
}
