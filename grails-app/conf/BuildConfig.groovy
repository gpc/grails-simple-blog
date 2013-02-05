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
		test 'org.gmock:gmock:0.8.2', 'org.hamcrest:hamcrest-library:1.1', {
			export = false
		}
	}

	plugins {
		build ':release:2.2.0', ':rest-client-builder:1.0.3', {
			export = false
		}

		compile ':feeds:1.5'
		compile ':commentable:0.8.1'
		compile ':taggable:1.0.1'
	}
}
