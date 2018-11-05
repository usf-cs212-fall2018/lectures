Jetty and Servlets
=================================================

These demos illustrate basic concepts related to embedded Jetty and servlets in Java.

## Jetty Setup ##

To add Jetty as a user library in Eclipse, you will need the aggregate `jar` files located at the following links:

- [`jetty-all-9.x-uber.jar`](https://repo1.maven.org/maven2/org/eclipse/jetty/aggregate/jetty-all/)

Look for the latest `9.x` release. The directory is sorted lexicographically not numerically (e.g. by text not number), so version numbers like `9.4.12` will appear before version numbers like `4.4.9` even though it is a later version. 

:bulb: _You no longer need to download the servlet `jar` files separately---they now come bundled in the `jetty-all` jar file._

You may also want to consider the Apache Commons Lang library, which has several useful utilities:

- [`commons-lang3-3.x.x.jar`](https://commons.apache.org/proper/commons-lang/download_lang.cgi)

See the [Adding User Party Libraries in Eclipse](https://usf-cs212-fall2018.github.io/guides/adding-user-libraries-in-eclipse.html) guide for details.

## Relevant Resources ##

The following API documentation may be useful:

- [Jetty v9.x (Stable)](https://www.eclipse.org/jetty/javadoc/current/index.html?overview-summary.html)

The following pre-recorded videos may be useful:

- [Dynamic Webpages - Setup](https://youtu.be/X5gr591JsLQ)
- [Dynamic Webpages - Context Server](https://youtu.be/kJDmEom17-Q)

The following other websites might be useful:

- [Jetty Development Guide - Chapter 21. Embedding](https://www.eclipse.org/jetty/documentation/current/advanced-embedding.html)
