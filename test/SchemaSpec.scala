import models.graphs.SemanticGraph
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.Play
import com.tinkerpop.blueprints._
import scala.collection.JavaConversions._

/*
A test to test how types work
 */
@RunWith(classOf[JUnitRunner])
class SchemaSpec extends SemanticSpec {

  "Schema" should {
    val learn = "learn"
    val manage = "manage"
    val discover="discover"
    val discStr = "discoveries"
    val peopleStr="people"

    "Add types" in new WithApplication{
      Play.application().isTest must beTrue
      val sg = prepareGraph
      val g = sg.g


      g.shutdown()
    }

    }
}
