class SimpleBlogGrailsPlugin {
	def version = '0.2.1'
	def grailsVersion = '1.1 > *'
	def dependsOn = [feeds:"1.4 > *", commentable:"0.7 > *", taggable:"0.4.2 > *"]
	def author = 'Graeme Rocher'
	def authorEmail = ''
	def title = 'Simple Blog'
	def description = 'Adds a simple blog interface to an application'
	def documentation = 'http://grails.org/plugin/simple-blog'
}
