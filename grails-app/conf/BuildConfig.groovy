grails.project.work.dir = 'target'
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

	inherits 'global'
	log 'warn'

	repositories {
		grailsCentral()
		mavenLocal()
		mavenCentral()
	}

	dependencies {
		test 'org.gmock:gmock:0.8.2', 'org.hamcrest:hamcrest-library:1.1'
	}

	plugins {
		build(":tomcat:$grailsVersion", ":release:1.0.0") {
			export = false
		}
	}
}
