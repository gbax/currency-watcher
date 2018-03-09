package currencywatcher

class UrlMappings {

    static mappings = {
        "/api/$controller/$action"{}
        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
