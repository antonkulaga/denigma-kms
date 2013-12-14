import graphs.schemes.constraints.{DateTimeOf, TextOf, StringOf}
import graphs.schemes.NodeType
import graphs.{SemanticGraph, SG}
import org.joda.time.DateTime
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.Play
import com.tinkerpop.blueprints._
import models._
import org.denigma.macroses.Model.Mappable
/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class PageSpec extends SemanticSpec {


  "Have page type" in new WithApplication{
    Play.application().isTest must beTrue
    val sg: SemanticGraph = prepareGraph
    val g = sg.g
    import SG._
    sg.nodeTypeVertex(Page.name).mustEqual(None)
    sg.nodeType(Page.name).mustEqual(None)
    sg.addType(Page,true)
    val uv: Option[Vertex] = sg.nodeTypeVertex(Page.name)
    uv match {
      case None=>failure("Page class has not been added to the graph")
      case Some(v)=>
        v.str(NAME) match {
          case Some(Page.name)=>"it is ok!"
          case None=>failure("no user name has been written to the graph")
          case Some(str)=>failure("Page name was written down in a wrong way")
        }

    }
    g.shutdown()
  }

  "add new page" in new WithApplication{
    Play.application().isTest must beTrue
    val sg: SemanticGraph = prepareGraph
    val g = sg.g
    import SG._
    sg.addType(Page,true)
    case class UnknownPage(title:String,text:String,published:DateTime)
    val dt = new DateTime(1000000)
    val wr = UnknownPage("some","text",dt)
//    SG
//
//    object Page extends NodeType("Page") with CreatedByUser
//    {
//      val title = must be StringOf("title")
//      val text = must be TextOf("text")
//      val published = must be DateTimeOf("published")
//
//      //  must have OutLinkOf(Created.name,Created.name,GP.USER)
//    }
//

    g.shutdown()
  }
}