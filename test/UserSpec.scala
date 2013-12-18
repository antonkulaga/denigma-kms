import graphs.{SemanticGraph, SG}
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
class UserSpec extends SemanticSpec {


  "Have user type" in new WithApplication{
    Play.application().isTest must beTrue
    val sg: SemanticGraph = prepareGraph
    val g = sg.g
    import SG._
    sg.nodeTypeVertex(User.name).mustEqual(None)
    sg.typeByName(User.name).mustEqual(None)
    sg.registerType(User,true)
    val uv: Option[Vertex] = sg.nodeTypeVertex(User.name)
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

  "Validate user type on properties" in new WithApplication{
    Play.application().isTest must beTrue
    val sg: SemanticGraph = prepareGraph
    val g = sg.g
    sg.nodeTypeVertex(User.name).mustEqual(None)
    sg.typeByName(User.name).mustEqual(None)
    sg.registerType(User,true)


    case class WrongUser1(wrongName:String,email:Int,password:Int) extends Model
    case class WrongUser2(username:String,email:String,password:String) extends Model
    case class RightUser(username:String,email:String,password:String) extends Model
    case class WrongUser3(usernamew:String,emailw:String,password:String) extends Model


    val items = User.must.items

    //must have StringOf("username") have HashOf("password")  have StringOf("email")


    val wu1 = WrongUser1("name",12,15).asMap
    val wu2 = WrongUser2("name","some#email.com","pass").asMap
    val ru = RightUser("name","some@meail.com","pass").asMap
    val wu3 = WrongUser3("name","some@meail.com","pass").asMap


    val wrong1 = User.must.validateProperties(wu1)
    val wrong2 = User.must.validateProperties(wu2)
    val r = User.must.validateProperties(ru)
    val wrong3 = User.must.validateProperties(wu3)

    User.must.propsAreValid(wu1) should beFalse

    wrong1.get("username").get shouldEqual None
    wrong1.get("email").get shouldEqual Some(false)
    wrong1.get("password").get shouldEqual Some(false)

    User.must.propsAreValid(wu2) should beFalse

    wrong2.get("username").get shouldEqual Some(true)
    wrong2.get("email").get shouldEqual Some(false)
    wrong2.get("password").get shouldEqual Some(true)

    User.must.propsAreValid(ru) should beTrue

    r.get("username").get shouldEqual Some(true)
    r.get("email").get shouldEqual Some(true)
    r.get("password").get shouldEqual Some(true)

    User.must.propsAreValid(wu3) should beFalse

    wrong3.get("username").get shouldEqual None
    wrong3.get("email").get shouldEqual None
    wrong3.get("password").get shouldEqual Some(true)



    g.shutdown()
  }

  "add user with properties" in new WithApplication{
    Play.application().isTest must beTrue
    val sg: SemanticGraph = prepareGraph
    val g = sg.g
    import SG._
    sg.registerType(User,true)
    case class RightUser(username:String,email:String,password:String) extends Model
    case class WrongUser2(username:String,email:String,password:String) extends Model
    case class AddUser(id:String,username2:String,email2:String,password:String) extends Model

    val user = RightUser("name","some@meail.com","pass")
    val wu = WrongUser2("name","some#email.com","pass")


    val wut = User.save(wu.asMap)
    wut must beFailedTry[Vertex]
    val rut = User.save(user.asMap)
    rut must beSuccessfulTry[Vertex]
    val res = rut.get.id
    val au  = AddUser(res,"username2","email2",password = "passwordNEW")

    val aut = User.save(au.asMap)
    aut must beSuccessfulTry[Vertex]


    g.shutdown()
  }
}
