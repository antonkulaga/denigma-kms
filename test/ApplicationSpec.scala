import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.mvc.SimpleResult
import play.api.test._
import play.api.test.Helpers._
import org.specs2.mock._
import scala.concurrent.Future

//
///**
// * Add your spec here.
// * You can mock out a whole application including requests, plugins etc.
// * For more information, consult the wiki.
// */
//@RunWith(classOf[JUnitRunner])
//class ApplicationSpec extends SemanticSpec  {
//
//  "Application" should {
//
//
//
//    "prepare the graph and the menu" in new WithApplication{
//      val sg = prepareGraph
//
//      //sg.url must contain ("plocal:./db/test")
//      val g = sg.g
//
//      val Some(result) = route(FakeRequest(GET, "/test"))
//
//      status(result) must equalTo(OK)
//      contentType(result) must beSome("text/html")
//      charset(result) must beSome("utf-8")
//      contentAsString(result) must contain("Hello World")
//      sg.g.shutdown()
//    }
//
////    "send 404 on a bad request" in new WithApplication{
////      route(FakeRequest(GET, "/boum")) must beNone
////    }
//
////    "send 404 on a bad request" in new WithApplication{
////      route(FakeRequest(GET, "/boum")) must beNone
////    }
//
////    "render the index page" in new WithApplication{
////      val home = route(FakeRequest(GET, "/")).get
////
////      status(home) must equalTo(OK)
////      contentType(home) must beSome.which(_ == "text/html")
////      contentAsString(home) must contain ("Your new application is ready.")
////    }
//
//
//  }
//}
