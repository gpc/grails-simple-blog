package org.grails.blog
import org.junit.After 
import org.junit.Before 
import org.junit.Test 
import org.grails.comments.*

import grails.test.*


class BlogEntryIntegrationTests extends GrailsUnitTestCase {
    def entry
    def poster
    
    @Before
    void before() {
        super.setUp()
        createNewEntry()
    }
    
    @After
    void after() {
        super.tearDown()
        entry.delete(flush:true)
    }

    void createNewEntry() {
        entry = new BlogEntry()
        entry.title = "blog Title"
        entry.body = "Dummy Entry"
        entry.dateCreated = new Date()
        entry.lastUpdated = new Date()
        entry.author = "Author"
       
        assertNotNull entry.save(flush:true)
        assertNotNull entry.id
    }

    @Test
    void testBlogEntryShouldBeginWithNoComments() {
        assertNotNull entry.getComments() 
        assertTrue entry.comments.empty
    }
    
    @Test
    void testBlogEntryShouldCanReceiveComments() {
        poster = new FakeUser()
        entry.addComment(poster, "MyComment")
        assertEquals 1, Comment.count()
        assertEquals 1, entry.comments.size()
    }
    
    @Test
    void testBlogEntryShouldBeAbleToReceiveTags() {
        entry.addTag("posts")
        entry.addTag("test")
        entry.addTag("videos")
        
        def tags =  ["posts", "test", "videos"]
        assertEquals tags, entry.tags
    }
    
    @Test
    void shouldBeSearcheableByTitle() {
        def result = BlogEntry.search("*title*")
        assertNotNull result
        print result.results
        assertEquals 1, result.results.size()
    }
    
    @Test
    void shouldBeSearcheableByAuthor() {
        def result = BlogEntry.search("Dummy")
        assertNotNull result
        print result.results
        assertEquals 1, result.results.size()
    }
    
    
    class FakeUser {
        int id
        String name
        
    }
}
