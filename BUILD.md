System Requirements
===================

* JDK 1.7 or greater (http://www.oracle.com/technetwork/java/)
* Gradle 2.4 or greater (http://gradle.org/)

Project Set-Up
==============

* Ensure that Gradle is on your path (set the PATH environment variable accordingly)


Building the project
====================

Gradle usual build folder ('build') in this project has been changed to 'target' (the same of Maven), 
so all build artifacts will be generated there.

* To compile all source files into binary class files:

		$ gradle build

or even to refresh dependencies

		$ gradle build --refresh-dependencies

note that binary jar is generated in the libs subfolder of build folder.

* To generate Javadoc

		$ gradle javadoc

* To generate Eclipse project files (optional)

		$ gradle eclipse


Note
====

None.


---
