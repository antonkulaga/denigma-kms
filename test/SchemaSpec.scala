import graphs.{SemanticGraph, SG}
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.Play
import com.tinkerpop.blueprints._

/*
A test to test how types work
 */
@RunWith(classOf[JUnitRunner])
class SchemaSpec extends SemanticSpec {


  val learn = "learn"
  val manage = "manage"
  val discover = "discover"
  val discStr = "discoveries"
  val peopleStr = "people"

  def prepareTest = {
    val sg: SemanticGraph = prepareGraph
    import SG._

    val root = sg.root(sg.ROOT, NAME -> sg.ROOT, "other" -> "otherval")

    root.addLink(learn, "name" -> learn)
    val people = sg.addNode(NAME -> peopleStr)
    val m = root.addLinkTo(manage, people, "name" -> manage)
    val discoveries = sg.addNode(NAME -> discStr)
    val d: Vertex = root.addGetLinkTo(discover, discoveries, "name" -> discover)
    val dd: Vertex = root.addGetLinkTo(discover, discoveries)
    sg.g.commit()
    sg
  }

  "Schema" should {
    val learn = "learn"
    val manage = "manage"
    val discover="discover"
    val discStr = "discoveries"
    val peopleStr="people"

    "Add types" in new WithApplication{
      Play.application().isTest must beTrue
      val sg = prepareTest
      val g = sg.g


      g.shutdown()
    }

    }
}
