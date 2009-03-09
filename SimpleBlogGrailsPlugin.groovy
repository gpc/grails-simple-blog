class SimpleBlogGrailsPlugin {
    // the plugin version
    def version = "0.1.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.1-SNAPSHOT > *"
    // the other plugins this plugin depends on
    def dependsOn = [feeds:"1.4 > *", commentable:"0.7 > *", taggable:"0.4.2 > *"]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def author = "Graeme Rocher"
    def authorEmail = ""
    def title = "Simple Blog"
    def description = '''\\
A plugin that adds a simple blog interface to an application
'''

    // URL to the plugin's documentation
    def documentation = "http://grails.org/SimpleBlog+Plugin"

    def doWithDynamicMethods = { ctx ->
        // TODO Implement registering dynamic methods to classes (optional)
    }

}
