<div id="entry${entry.id}" class="blogEntry">
	<g:render template="/blogEntry/entryTitle" model="[entry:entry]" plugin="simple-blog"></g:render>
	<div class="entryBody">
		${entry.body}
	</div>
</div>