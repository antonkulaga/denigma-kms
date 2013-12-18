import com.tinkerpop.blueprints._
import graphs.{SemanticGraph, SG}
import models._
import org.denigma.macroses.Model.Mappable
import org.joda.time.DateTime
import org.junit.runner._
import org.specs2.runner._
import play.Play
import play.api.test._
import scala.Some


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
    sg.typeByName(Page.name).mustEqual(None)
    sg.registerType(Page,true)
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
    sg.registerType(Page,true)
    case class UnknownPage(title:String,text:String,published:DateTime) extends Model
    val dt = new DateTime(1000000)
    val wr = UnknownPage("some","text",dt)

    Page.save(wr.asMap) must beFailedTry[Vertex]

    case class NicePage(title:String,text:String,published:DateTime) extends Model


//    val user = RightUser("name","some@meail.com","pass")
//    val wu = WrongUser2("name","some#email.com","pass")
//
//
//    val wut = User.write(wu.asMap)
//    wut must beFailedTry[Vertex]
//    val rut = User.write(user.asMap)
//    rut must beSuccessfulTry[Vertex]
//    val res = rut.get.id
//    val au  = AddUser(res,"username2","email2",password = "passwordNEW")
//
//    val aut = User.write(au.asMap)
//    aut must beSuccessfulTry[Vertex]


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