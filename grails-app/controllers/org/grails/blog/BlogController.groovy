package org.grails.blog

class BlogController {
	
	def afterInterceptor = { model ->
		def layout = grailsApplication.config.blog.page.layout
		if(layout) model?.layout = layout
	}

	def list = {
		def date = null // TODO: Work out date range from params
		def today = new Date()
		def author = params.author
		def entries
		def totalEntries
		if(author && date) {
			entries = BlogEntry.withCriteria {
				eq 'author', author
				eq 'published', true
				if(params.year)
					between 'dateCreated', date, today
				order "dateCreated", "desc"
				maxResults 10
				cache true
			}
			totalEntries = BlogEntry.createCriteria().get {
				projections { rowCount() }
				eq 'author', author
				eq 'published', true
				if(params.year)
					between 'dateCreated', date, today
				cache true
			}
		}
		else {
			entries = findRecentEntries()
			totalEntries = BlogEntry.countByPublished(true, [cache:true])
		}
		
		def authors = findBlogAuthors()
		render view:"/blogEntry/list", model:[entries: entries, 
											  authors:authors, 
											  tagNames: BlogEntry.allTags,
											  totalEntries:totalEntries ]
	}
	
	private findBlogAuthors() {
		BlogEntry.withCriteria {
			projections { distinct "author" }
			cache true
		}		
	}
	private findRecentEntries() {
		BlogEntry.findAllByPublished(true,[max:10, cache:true, offset:params.offset, order:"desc", sort:"dateCreated"])
	}
	
	def feed = {
		def entries = findRecentEntries()
		def feedOutput = {
			title = g.message(code:"blog.feed.title", 'default':"Recent Blog Posts")
			description = g.message(code:"blog.feed.description", 'default':"Recent Blog Posts")			
			link = g.createLink(absolute:true, controller:"blog", action:"list")
			
			for(e in entries) {
				entry(e.title) {
					link = g.createLink(absolute:true, controller:"blog", action:"showEntry", params:[title:e.title, author:e.author])
					e.body
				}
			}
		}
		if(['rss','atom'].contains(params.format))
			render(feedType:params.format, feedOutput)		
		else
			render(feedType:"rss", feedOutput)		
	}
	
	def createEntry = {		
		def entry = new BlogEntry( request.method == 'POST' ? params['entry'] : [:])
		
		render view:"/blogEntry/create", model:[entry:entry]
	}
	
	def publish = {
		if(request.method == 'POST') {
			def entry = BlogEntry.get(params.id) ?: new BlogEntry()
			BlogEntry.withTransaction {
				entry.properties = params['entry']
				def authorEvaluator = grailsApplication.config.blog.author.evaluator
				if(authorEvaluator instanceof Closure) {
					authorEvaluator.delegate = this
					authorEvaluator.resolveStrategy = Closure.DELEGATE_FIRST
					entry.author = authorEvaluator.call()?.toString()
				}
				entry.published = true
				if(entry.save()) {
					entry.parseTags(params.tags, ",")
					redirect(action:"showEntry", params:[title:entry.title, author:entry.author])
				}
				else {
					render view:"/blogEntry/create",model:[entry:entry]
				}				
			}

		}
	}
	
	def editEntry = {
		def entry = BlogEntry.get(params.id)
		if(entry) {
			render view:"/blogEntry/create", model:[entry:entry]
		}
		else {
			response.sendError 404
		}
	}
	def showEntry = {
		def entry = BlogEntry.findByAuthorAndTitle(params.author, params.title)
		if(entry) {
			render view:"/blogEntry/entry", model:[entry:entry]
		}
		else {
			response.sendError 404
		}
	}
	
	def search = {
		try {
			def searchResult = BlogEntry.search(params.q)			
			
			render(view:"/blogEntry/list", model:[entries:searchResult.results,
									   authors:findBlogAuthors(),
									   tagNames: BlogEntry.allTags,
									   totalEntries:searchResult.total])
		}
		catch(e) {
			// ignore, searchable not installed
			log.error "Search error ${e.message}",e
			[authors:findBlogAuthors(), tagNames:BlogEntry.allTag, totalEntries:0]
		}
	}
	
	def byAuthor = {
		
	}
	
	def byTag = {
		if(params.tag) {
			def entries = BlogEntry.findAllByTag(params.tag.trim(), [max:10, offset:params.offset, sort:"dateCreated", order:"desc"])

			render view:"/blogEntry/list", model:[entries:entries.findAll { it.published },
												  authors:findBlogAuthors(), 
			  									  tagNames: BlogEntry.allTags,
												  totalEntries:BlogEntry.countByTag(params.tag.trim())]			
		}
		else {
			redirect action:"list"
		}
		
	}

}