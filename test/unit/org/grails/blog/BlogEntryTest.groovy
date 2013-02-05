package org.grails.blog

import grails.test.GrailsUnitTestCase

import org.junit.After
import org.junit.Before
import org.junit.Test

class BlogEntryTest extends GrailsUnitTestCase {
	def entry

	@Before
	void setUp() {
		super.setUp()
		mockDomain(BlogEntry)
		entry = new BlogEntry()
	}

	@Test
	void shouldNotSalveEmptyObject() {
		def result = entry.save()
		assertNull result
		assertNotNull entry.errors
	}

	@Test
	void testShouldValidateFieldsTitleAndBody() {
		assertFalse entry.validate()
		assertNotNull entry.errors
		assertEquals "nullable", entry.errors["title"]
		assertEquals "nullable", entry.errors["body"]
	}
}
