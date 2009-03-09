package org.grails.blog

class BlogTagLib {

	static namespace = "blog"

	def renderEntries =  { attrs, body ->
		def max = attrs.number ?: 5
		
		def entries = BlogEntry.list(max:max, cache:true, order:"desc", sort:"dateCreated")
		
		out << g.render(template:"/blogEntry/recentEntries", plugin:"simple-blog",model:[entries:entries])
	}
}