
class BlogUrlMappings {

    static mappings = {
        "/blog"(controller:"blog", action: "list")
        
        "/blog/$author?/$year?/$month?/$day?"(controller:"blog", action:"list") {
            constraints {
                year matches:/\d{4}/
                month matches:/\d{2}/           
                day matches:/\d{2}/                             
            }
        }
        "/blog/view/$author/$title?"(controller:"blog", action:"showEntry") 
        "/blog/tagged/$tag"(controller:"blog", action:"byTag")
        "/blog/entry/create"(controller:"blog", action:"createEntry")
        "/blog/entry/publish/$id?"(controller:"blog", action:"publish")     
        "/blog/entry/edit/$id"(controller:"blog", action:"editEntry")   
        "/blog/search"(controller:"blog",action:"search")   
        "/blog/feed/$format"(controller:"blog", action:"feed")
    }

}