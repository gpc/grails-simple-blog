<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta name="layout" content="${layout ?: 'main'}" />
		<plugin:isAvailable name="feeds">
			<feed:meta kind="rss" version="2.0" controller="blog" action="feed" params="[format:'rss']"/>		
		</plugin:isAvailable>
		
		<title><g:message code="blog.list.title" default="Blog Home" /></title>	
	</head>
	<body>
        <div class="blogControls">
            <div class="menuButton"><g:link class="create" controller="blog" action="createEntry"><g:message code="grails.blog.createEntry.link" default="Create Entry"></g:message></g:link></div>

			<plugin:isAvailable name="feeds">
            	<div class="menuButton">
					<g:link class="feed" controller="blog" action="feed" params="[format:'rss']">
						<g:message code="grails.blog.rss.link" 
								   default="RSS Feed"></g:message>
					</g:link>
				</div>			
			</plugin:isAvailable>
        </div>
		
		<plugin:isAvailable name="searchable">
			<div class="searchBox">
				<g:form url="[controller:'blog', action:'search']">
					<g:textField name="q"></g:textField> 
					<g:submitButton name="${message(code:'blog.search.button', 'default':'Search Blogs')}" />
				</g:form>
			</div>
		</plugin:isAvailable>
		
		<div class="blogQuickLinks">
			<div id="blogAuthors" class="blogAuthors">
				<h2 class="authorsTitle">
					<g:message code="blog.authors.title" default="Blog Authors"></g:message>
				</h2>
				<div class="authorsList">
					<g:each var="author" in="${authors}">				
						<div class="author">
							<g:link controller="blog" action="showEntry" params="[author:author]">${author}</g:link>
						</div>						
					</g:each>					
				</div>
			</div>

			<div id="blogTags" class="blogTags">
				<h2 class="tagsTitle">
					<g:message code="blog.tags.title" default="Blog Tags"></g:message>
				</h2>

				<div class="tagList">
					<g:each var="tag" in="${tagNames}">
						<div class="tag">					
							<g:link controller="blog" action="byTag" params="[tag:tag]">${tag}</g:link>
						</div>							
					</g:each>					

				</div>
			</div>
			
		</div>
				
		<div id="blogEntries" class="blogEntries">
			<g:set var="first" value="${entries ? entries.iterator().next() : null}" />
			<div class="firstEntry">
				<g:if test="${first}">
					<g:render template="/blogEntry/entry" model="[entry:first]" plugin="simple-blog"></g:render>
				</g:if>							
			</div>
			<g:set var="remaining" value="${entries-first}"></g:set>
			<g:if test="${remaining}">
				<g:render template="/blogEntry/recentEntries" model="[entries:remaining]" plugin="simple-blog" />
			
			</g:if>
		</div>
		

	</body>
</html>