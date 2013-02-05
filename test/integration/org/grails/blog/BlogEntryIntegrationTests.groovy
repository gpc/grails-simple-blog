package org.grails.blog

import org.grails.comments.Comment

class BlogEntryIntegrationTests extends GroovyTestCase {

    private BlogEntry entry = new BlogEntry(title: "blog Title", body: "Dummy Entry", author: "Author")
    private FakeUser poster

    @Override
    protected void setUp() {
        super.setUp()
        createNewEntry()
    }

    private void createNewEntry() {
        assertNotNull entry.save(flush:true)
        assertNotNull entry.id
    }

    void testBlogEntryShouldBeginWithNoComments() {
        assertNotNull entry.getComments()
        assertTrue entry.comments.empty
    }

    void testBlogEntryShouldCanReceiveComments() {
        poster = new FakeUser()
        entry.addComment(poster, "MyComment")
        assertEquals 1, Comment.count()
        assertEquals 1, entry.comments.size()
    }

    void testBlogEntryShouldBeAbleToReceiveTags() {
        entry.addTag("posts")
        entry.addTag("test")
        entry.addTag("videos")

        assertEquals(["posts", "test", "videos"], entry.tags)
    }

    void shouldBeSearcheableByTitle() {
        def result = BlogEntry.search("*title*")
        assertNotNull result
        print result.results
        assertEquals 1, result.results.size()
    }

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
