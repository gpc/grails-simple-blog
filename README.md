# Simple Blog Plugin

This plugin provides a simple blog feature, allowing users to create posts
with a WYSIWYG editor and tag them. Users can append comments to posts and subscribe to the blog via RSS/Atom.

## Dependencies

This plugin has the following dependencies:

* [http://www.grails.org/plugin/taggable](Taggable Plugin)
* [http://www.grails.org/plugin/commentable](Commentable Plugin)
* [http://grails.org/plugin/feeds](Feeds Plugin)

### Optional Searchable Dependency

The plugin does not declare a dependency on the searchable plguin, but the `BlogController` provides a `search` action which uses this plugin to search the blog entries if it's installed.

## Usage

### Installation

Add the following to `BuildConfig.groovy`

````groovy
runtime ":simple-blog:0.3.3"
````

After installation you will have a controller to access:

    http://localhost:8080/<your-application>/blog

### User Evaluator

In order to use this plugin you need to define a closure in `grails-app/conf/Config.groovy` that returns the current user. For example if the current user is stored in the session under the key `user`, the following would be appropriate

    grails.blog.author.evaluator = { session.user }

Alernatively, if the current user is available on a request-scoped attributed named `user` use the following:

    grails.blog.author.evaluator = { request.user }

Another option is to define a simple string to be added:

    grails.blog.author.evaluator = { "defaultUser" }

As a final example, if using the Spring Security plugin with a user domain class `com.example.User` whose identity property is named `id`, use the following:

````groovy
grails.blog.author.evaluator = {

    def principal = org.springframework.security.core.context.SecurityContextHolder.context.authentication.principal

    if (principal.hasProperty('id')) {

        def currentUserId = principal.id
        if (currentUserId) {
            com.example.User.get(currentUserId)
        }
    }
}
````

#### Commentable

The Commentable plugin uses the same mechanism for determining the current user, so if you also have this plugin installed you can establish the current user for both plugins with:

````groovy
def evaluator = {
  // add code that returns the current user here
}

grails.blog.author.evaluator = evaluator
grails.commentable.poster.evaluator = evaluator
````


### URL Mappings

Useful available links:

* `/blog/$author?/$year?/$month?/$day?` - List entries (author and date range optional args)
* `/blog/tagged/$tag` - List entries with tag
* `/blog/feed/[rss|atom]` - Rss Feed

### Blog Taglib

#### &lt;blog:renderEntries&gt;

To render the last 3 entries:

    <blog:renderEntries number="3" />

The rendering of each entry is specified by the template `/blogEntry/recentEntries`. If the `number` attribute is
omitted, it defaults to 5.

#### &lt;blog:recentEntryLinks&gt;

To render links to the last 3 entries:

````
<ul>
    <blog:recentEntryLinks number="3">
        <li>
            <g:link controller="blog" action="showEntry" params="[title: it.title, author: it.author]">
                ${it.title}, <g:formatDate date="${it.publishedOn}"/>
            </g:link>
        </li>
    </blog:recentEntryLinks>
</ul>
````

The tag body is passed an instance of `BlogPostSummary` that may be referenced as `${it}` in order to render
each entry. If the `number` attribute is omitted, links to all published entries will be rendered.

#### &lt;blog:countEntries&gt;

Returns the number of published blog entries.

### Skinning

The plugin doesn't come with a layout or css defined, you will need to define these within your application by providing
styles for CSS classes like "blogControls", "blogQuickLinks", "blogTags", etc

## Plugin History

### Version 0.3.5
* [Fix bug](https://github.com/gpc/grails-simple-blog/issues/6) when finding blog posts by author. As a consequence of 
this fix, links such as `<g:link controller="blog">Blog</g:link>` should be replaced by `<g:link controller="blog" action="list">Blog</g:link>`
or `<g:link uri="/blog">Blog</g:link>`. The link `<g:link controller="blog">Blog</g:link>` generates a relative URL 
`/blog/index` which will result in an attempt to find posts by an author named "index"

### Version 0.2.2
* Blog post URLs no longer change after entry is updated

### Version 0.2.1
* Bug fix: blank search query string was throwing a org.apache.lucene.queryParser.ParseException

### Version 0.2.0
* Using Grails 2.0.1
* Added ability to delete blog entries
* Added missing dependencies

### Version 0.1.4
* Using Grails 1.3
* Created Wiki documentation
* Improvements on urlMapping
* Created unit/integration tests
* Plugin now works more out of the box (straight forward installation and usage)

