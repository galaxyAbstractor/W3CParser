## W3C parser

This parser parses specifications released by W3C.

## Parse flow

First, open up `net/pixomania/crawler/db/hibernate.cfg.xml` and make sure the database name, host, username and password
are correct for your MySQL database. Also make sure to import the w3cparser.sql schema.

Secondly, we need to create some parsers. Generally, one parser corresponds to one kind of data that might be present on
the specification page. One parser contains one or several rules, and in some cases specific rules for one (or several)
URLs.

The mission of a parser rule is to locate the correct element on the page being parsed and process that element, and
return a value of type T (A string, a list, and so on). Each rule implements the
`net/pixomania/crawler/parser/rules/Rule<T>` interface which defines the `public T run(String url, Document doc)` method.

A parser rule uses [jSoup](http://jsoup.org/) to parse the HTML. If no element is found, `null` should be returned, in
which case the parser continues with the next rule until a valid result is found.

Already created parsers for W3C specs are to be found at `net/pixomania/crawler/W3C/parser/rules`.

Parsers are then registered in the `parsers` HashMap in `net/pixomania/crawler/W3C/W3C`, for example:

```
parsers.put("date", new Parser(new DateRule1()));
```

Here, we define a parser that can be fetched by the name "date", and create a `Parser`
(`net/pixomania/crawler/parser/Parser`) and send in all our related rules as arguments.

When each parser is defined, we can take a look at the `net/pixomania/crawler/W3C/ParserRunnable` class. We can see
how parsers are fetched and the `run()` method being called with the current URL and a fetched `Document` (jSoup).
`Parser` returns a `Result`, which contains which rule provided the result, and the actual result. This is then saved
to an object, which then is saved to the MySQL database using Hibernate.

Back to `net/pixomania/crawler/W3C/W3C`. When parsers have been defined, it's time to define standards. This is done
by adding `net/pixomania/crawler/W3C/datatypes/Standard` objects to the `standards` list. For example:

```
standards.add(new Standard(new String[]{"MathML"}, "http://www.w3.org/TR/MathML/"));
```

The first argument of `Standard` is a String array containing all URL names of the standard. This is the name found
in the last part of the URL. If a standard changes name during the course (but is still counted as the same standard),
all names should be listed in this array. If they aren't, any specification that doesn't match the names found in the
array are considered orphans until a standard is defined with a name that matches the orphan names.

In some cases we want to apply a specific rule to one or a few specific URLs. This can be done in two different ways
using two methods on the `Parser` object: `setRuleOnURL()` which sets a specific rule on a single URL and
`setRuleOnURLs()` which sets the same rule on multiple URLs at once.

The two different ways to define a specific rule is either by creating a new class or by using lambdas. For example:

```
parsers.get("editors").setRuleOnURL("http://www.w3.org/TR/1998/WD-xml-stylesheet-19981001", (url, doc) -> {
	ArrayList<Person> persons = new ArrayList<>();

	Person p1 = new Person();
	p1.setName("James Clark");
	p1.setEmail("jjc@jclark.com");
	p1.setFull("James Clark (jjc@jclark.com)");

	persons.add(p1);
	return persons;
});
```

or

```
parsers.get("date").setRuleOnURL("http://www.w3.org/TR/REC-CSS1/", (url, doc) -> "2008-04-11");
```

as the lambda approach, or:

```
parsers.get("editors").setRuleOnURLs(new String[]{"http://www.w3.org/TR/REC-MathML/",
		"http://www.w3.org/TR/1998/REC-MathML-19980407",
		"http://www.w3.org/TR/1998/PR-math-19980224",
		"http://www.w3.org/TR/WD-math-980106",
		"http://www.w3.org/TR/WD-math-970710",
		"http://www.w3.org/pub/WWW/TR/WD-math-970515"
}, new SpecificEditorsRule7());
```

as the class approach. Setting specific rules can be useful for **manual data input** in cases where it would be
inconvenient to write a complete parser to fetch some data.

Next we have the `extraLinks` list. Here we can add URLs that should be parsed but otherwise aren't. By default,
only URLs beginning with `http://www.w3.org/TR/` are parsed so not to parse non-specs, but in some cases, W3C
does not always follow this URL structure.

And lastly, we have the `linkReplacer` HashMap. This is used to replace invalid URLs in W3C specs. They can either
mismatch a format, 404, redirect or other things, and needs to be replaced.
