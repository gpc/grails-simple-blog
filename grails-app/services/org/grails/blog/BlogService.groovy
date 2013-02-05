package org.grails.blog

import org.springframework.transaction.annotation.Transactional

@Transactional(rollbackFor = Throwable.class, readOnly = true)
class BlogService {

    List<BlogPostSummary> recentPosts(Integer resultsLimit = null) {

        List<List<String>> blogEntries = BlogEntry.withCriteria {
            eq('published', true)
            order("dateCreated", "desc")

            if (resultsLimit) {
                maxResults(resultsLimit)
            }

            projections {
                property "title"
                property "author"
                property "dateCreated"
            }
            cache true
        }

        blogEntries.collect {
            new BlogPostSummary(title: it[0], author: it[1], publishedOn: it[2])
        }
    }
}


class BlogPostSummary {
    String title
    String author
    Date publishedOn
}
