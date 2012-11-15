[Greenvine](http://eusa.github.com/greenvine/index.html) [![Build Status](https://secure.travis-ci.org/eusa/greenvine.png)](http://travis-ci.org/eusa/greenvine)
==========
A tool for reverse-engineering a JPA 2.0 and/or Hibernate project from a JDBC database. It's a Maven plugin.

 - Entity classes with JPA 2.0 annotations
 - Hibernate mapping XML documents
 - DAO interfaces and implementation classes based on Spring DAO templates
 - Spring configurations for JPA EntityManager, Hibernate SessionFactory, DAO classes
 - Unitils test cases (integration tests with database)
 - DbUnit test data for test cases
 - Maven POM with all dependencies
 
Basically, you reverse engineer the database using the Maven plug-in.
mvn greenvine:revgen

Then, go to the output directory and type mvn test.

The generated project should compile and all tests should run without any modifications, including real database integration tests with real data.

COPYRIGHT AND LICENCE

Copyright (C) 2011 Patrick van Kann

Uses the Apache licence.
