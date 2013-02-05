package org.grails.blog

import org.grails.comments.Commentable
import org.grails.taggable.Taggable

class BlogEntry implements Taggable, Commentable {

	String author
	String title
	String body
	Boolean locked = false
	Boolean published = false

	Date dateCreated
	Date lastUpdated

	static searchable = [only:["title","body", "author"]]

	static constraints = {
		title blank:false
		body blank:false
	}

	static mapping = {
		body type:"text"
		cache true
	}
}
