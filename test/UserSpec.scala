import models.graphs.{SG, SemanticGraph}
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.Play
import com.tinkerpop.blueprints._
import scala.collection.JavaConversions._
import models.User
import org.specs2.matcher.ThrownMessages

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class UserSpec extends SemanticSpec {
  "Have user type" in new WithApplication{
    Play.application().isTest must beTrue
    val sg: SemanticGraph = prepareGraph
    val g = sg.g
    import SG._
    sg.nodeTypeVertex(User.name).mustEqual(None)
    sg.nodeType(User.name).mustEqual(None)
    sg.addType(User,true)
    val uv = sg.nodeTypeVertex(User.name)
    uv match {
      case None=>failure("User class has not been added to the graph")
      case Some(v)=>
        v.str(NAME) match {
          case Some(USER)=>"it is ok!"
          case None=>failure("no user name has been written to the graph")
          case Some(str)=>failure("User name was written down in a wrong way")
        }

    }


    g.shutdown()
  }
}
//object User extends NodeType(GP.USER)
//{
//  must have StringOf("username") have HashOf("password")  have StringOf("email")
//  should have OutLinkOf("LiveIn",City.name)
//  should have OutLinkOf(Role.name)
//}