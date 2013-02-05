<h2 class="entryTitle">
	<g:link controller="blog" action="showEntry" params="[title:entry.title, author:entry.author]">${entry.title}</g:link>
</h2>
<div class="entryDetails">
	<g:formatDate date="${entry.dateCreated}" format="MMMMM dd, yyyy" /> by
	<g:link controller="blog" action="${entry.author}">${entry.author}</g:link> |
	<g:each status="i" var="tag" in="${entry.tags}">
		<g:link controller="blog" action="tagged" id="${tag}">${tag}</g:link><g:if test="${i<entry.tags.size()-1}">, </g:if>
	</g:each> |
	<g:link controller="blog" action="showEntry"
	        params="[author:entry.author, title:entry.title]"
	        fragment="comments">${entry.totalComments} comments.</g:link>
</div>
