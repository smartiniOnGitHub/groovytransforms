groovytransforms - TODO
=======================

TODO
----

- for post-0.3 (test uploaded artifacts and update related docs/info):
	- in README.md, add reference to binary artifacts to download them in a simple way (even by hand) ...
	- test download of published jar from JCenter, maybe with a Groovy Script under src/test/console/ ...
	- test from other projects (maybe at the beginning from a local jar), like Grails-Scala Plugin ...

- for 0.4:
	- update to a newer Groovy release, like that used in Grails 2.5.x (Groovy 2.4.3, last before Groovy going at Apache) ...

- for 0.5:
	- update build.gradle to force minimum Java version to 1.7 ...
	- set default Scala version to latest stable 2.11.x ...


- for other releases:
	- move (if possible) ScalifyASTTransformation from org.codehaus.groovy.transform to a project package ...
	- remove ScalaObject deprecations ...
	- add more features ...
	- in build.gradle, add project developers and project license (with reference to Apache License URL etc) ...
	- in build.gradle, add references to project home at GitHUB, and scm (urls) via Git ...
	- in build.gradle, add all stuff for automatic publishing at bintray (signing artifacts, license info, etc) ...
	- like many Scala-related libraries, check if use a scalaSuffix in generated artifacts (_2.10 and _2.11) and generate them separately ... no, not needed
	- make a branch for scala-2.10.x (but only if/when needed, from related tags), and keep master of 2.11.x ... ok


- etc ...

---------------


DONE
----

- 0.2 (last version done by original author, Andres Almiray back in 2013):
	- tag here as my starting point ... ok

- for 0.3 (maintenance with small updates, update dependencies and remove some deprecations):
	- put artifacts in a public area at BinTray, then link at JCenter, to be able to get/consume them for example from Grails Plugins ... ok
	- pom: add a pom file (updated by hand, from previous 0.2 version), to be able to link/publish artifacts even at JCenter ... ok
	- pom: include it in versioned files (just for backup purposes) ... ok
	- pom: add inside it even the dependency on related Groovy version, just to make it explicit (I hope it will be a good addition here) ... ok
	- little update for project structure, and add other support files ... ok
	- check if change package name ... no, I think the current is good (unless objections from someone)
	- check if change project name ... no, I think the current is good (unless objections from someone)
	- update to latest stable Gradle (currently 2.4) ... ok
	- update to a newer Groovy release, like 2.0.8 (used in Grails 2.2.5) ... ok
	- update to a newer Scala release, like 2.10.5 ... ok
	- add Gradle wrapper, to simplify project build/usage from sources ... ok
	- cleanup old/unused code in build.gradle, even because deploy of generated artifacts will be done by hand ... ok
	- add license header in all source files, and remove author tags in sources (best practice for example at Apache) ... ok
	- update build.gradle to use versions from gradle.properties, or move in an allprojects block inside build.gradle ... ok
	- small code cleanup ... ok
	- add LICENSE and NOTICE in generated (build) artifacts (at least by hand for now) ... ok (automatically added)
	- fix doc for Gradle command to generate binary jar (in BUILD.md) ... ok
	- additional artifacts generation (and with LICENSE and NOTICE even in their root): even sources and javadoc jars ... ok
	- update code to remove Scala deprecations ... ok but partial, because ScalaObject has been deprecated but there isn't a real alternative, check later for a better long-term solution
	- update/commit changelog ... ok
	- tag the release ... ok


---------------
