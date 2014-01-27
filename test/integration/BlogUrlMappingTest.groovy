import grails.test.GrailsUrlMappingsTestCase
import org.junit.Test

class BlogUrlMappingTest extends GrailsUrlMappingsTestCase {
    static mappings = BlogUrlMappings

    @Test
    void confirmingAccessProblemOnURLMapping() {
        assertForwardUrlMapping("/blog", controller:"blog", action:"list")
    }

    @Test
    void accessListActionThroughDateParamsReverse() {
        assertReverseUrlMapping("/blog/author1/2007/10/10", controller:"blog", action: "list") {
            author = "author1"
            year = 2007
            month = 10
            day = 10
        }
    }

    @Test
    void accessListActionThroughDateParams() {
        assertForwardUrlMapping("/blog/author1/2007", controller:"blog", action: "list") {
            author = "author1"
            year = 2007
        }
    }
}