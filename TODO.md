groovytransforms - TODO
=======================

TODO
----

- for 0.3 :
	- update code to remove Scala deprecations ... wip
	- put artifacts in a public area at JCenter, to be able to get/consume them for example from Grails Plugins ...
	- test from other projects, like my Grails-Scala Plugin ...
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


- for other releases:
	- move (if possible) ScalifyASTTransformation from org.codehaus.groovy.transform to a project package ...
	- update build.gradle to force minimum Java version to 1.7 ...
	- make a branch for scala-2.10.x, and keep master of 2.11.x ...
	- set default Scala version to latest stable 2.11.x ...
	- add LICENSE and NOTICE in generated (build) artifacts ...
	- like many Scala-related libraries, check if use a scalaSuffix in generated artifacts (_2.10 and _2.11) and generate them separately ...
	- add more features ...
	- update to a newer Groovy release, like that used in Grails 2.5.x ...


- etc ...

---------------


DONE
----

- 0.2 (last version done by original author, Andres Almiray back in 2013):
	- tag here as my starting point ... ok


---------------
