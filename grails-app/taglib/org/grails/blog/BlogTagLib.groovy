package org.grails.blog

class BlogTagLib {

	static namespace = "blog"

    BlogService blogService

    def renderEntries =  { attrs ->
		def max = attrs.number ?: 5

		def entries = BlogEntry.list(max:max, cache:true, order:"desc", sort:"dateCreated")
		out << g.render(template:"/blogEntry/recentEntries", plugin:"simple-blog",model:[entries:entries])
	}

    /**
     * Renders recent blog entries. The tag body is passed an instance of <tt>BlogPostSummary</tt> that may be
     * referenced as <tt>${it}</tt> in order to render each entry
     *
     * @attr number Maximum number of entries to render. If omitted, all published entries will be rendered
     */
    def recentEntryLinks = { attrs, body ->

        List<BlogPostSummary> entries = blogService.recentPosts(attrs.number?.toInteger())

        entries.each {BlogPostSummary blogPost ->
            out << body(blogPost)
        }
    }

    def countEntries = {attrs ->
        BlogEntry.countByPublished(true, [cache: true])
    }
}