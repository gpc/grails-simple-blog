package org.grails.blog

import java.util.Date;
import static org.hamcrest.Matchers.*;
import org.apache.commons.collections.map.LinkedMap.LinkedMapList 
import org.gmock.WithGMock;
import org.junit.Before 
import org.junit.Test;

import grails.test.ControllerUnitTestCase 

@WithGMock
class BlogControllerTest extends ControllerUnitTestCase {
	def entry
	
	@Before
	void setUp() { 
	    super.setUp()
		entry = new BlogEntry(
				published:true,
				title: "title",
				body: "body",
				author: "victor",
				dateCreated: new Date(),
				lastUpdated: new Date()  )
		
		mockDomain(BlogEntry, [entry])
	}
	
	@Test
	void listActionShouldReturnAllPostsPublishedOrderedByDate() {
		// Patching methods that don't need to use
		BlogEntry.metaClass.static.getAllTags = { null }
		controller.metaClass.findBlogAuthors = { null }
		def model = controller.list()
		
		assertNotNull model.entries
		assertEquals 1, model.totalEntries
	}
	
	@Test
	void listActionShouldNotListUnpublishedEntries() {
		// Patching methods that don't need to use
		BlogEntry.metaClass.static.getAllTags = { null }
		controller.metaClass.findBlogAuthors = { null }
		entry.published = false
		entry.save(flush:true) 
		
		def model = controller.list()
		
		assertNotNull model.entries
		assertEquals 0, model.entries.size()
		assertEquals 0, model.totalEntries
	}
	
	@Test
	void findBlogAuthors() {
		
		def results = ["victor"]
		               
		controller.metaClass.findRecentEntries = {null}
		mock(BlogEntry).static.getAllTags().returns(null)
		mock(BlogEntry).static.countByPublished(any(boolean), any(Object)).returns(0)
		mock(BlogEntry).static.withCriteria(instanceOf(Closure)).returns(results) 
		play {
			def model = controller.list()
			assertNotNull model.authors
			assertEquals results, model.authors
		}
	}
	
	@Test
	void shouldBeAbleToTransformDateParamsIntoDate() {
	    
	    mockParams.year = 2009
	    mockParams.month = 03
	    mockParams.day = 01
	    
	    def date = new Date().parse("yyyy/MM/dd", "2009/03/01")
	    
	    assertEquals date, controller.parseDateParams(mockParams)
	}
	
	@Test
    void shouldBeAbleToTransformDateParamsIntoDateWithoutDayParam() {
        
        mockParams.year = 2009
        mockParams.month = 02
        
        def date = new Date().parse("yyyy/MM/dd", "2009/02/01")
        
        assertEquals date, controller.parseDateParams(mockParams)
    }
	
    @Test
    void shouldReturnNullDateIfYearNotGiven() {
        assertNull controller.parseDateParams(mockParams)
    }
}
