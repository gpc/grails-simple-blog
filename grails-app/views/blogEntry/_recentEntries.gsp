<div class="recentEntries">
	<h2><g:message code="blog.recent.entries.title" default="Recent Entries:"></g:message></h2>
	<g:each var="entry" in="${entries}">
			<div class="recentEntry">
				<g:render template="/blogEntry/entryTitle" model="[entry:entry]" plugin="simple-blog"></g:render>
			</div>
	</g:each>
</div>