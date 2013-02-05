<html>

	<head>
		<meta http-equiv="Content-type" content="text/html; charset=utf-8">
		<meta name="layout" content="${layout ?: 'main'}" />
		<title>${entry.title}</title>

		<plugin:isAvailable name="grails-ui">
			<gui:resources components="richEditor"/>
		</plugin:isAvailable>

	</head>
	<body id="entry">
      <div class="blogControls">
         <div class="menuButton"><g:link class="list" controller="blog" action="list"><g:message code="grails.blog.list.link" default="Blog Home"></g:message></g:link></div>
         <div class="menuButton"><g:link class="edit" controller="blog" action="editEntry" id="${entry.id}"><g:message code="grails.blog.edit.link" default="Edit Entry"></g:message></g:link></div>
         <div class="menuButton"><g:link class="edit" controller="blog" action="deleteEntry" id="${entry.id}"><g:message code="grails.blog.delete.link" default="Delete Entry"></g:message></g:link></div>
		</div>

		<div class="blogEntryDisplay">
			<g:render template="/blogEntry/entry" plugin="simple-blog" model="[entry:entry]"></g:render>

			<div id="comment" class="entryComments">
				<h2><g:message code="grails.blog.comments.title" default="Recent Comments:"></g:message></h2>
				<comments:render bean="${entry}" />
			</div>
		</div>
	</body>
</html>
