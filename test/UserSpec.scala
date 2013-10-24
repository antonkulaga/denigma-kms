import models.graphs.SemanticGraph
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.Play
import com.tinkerpop.blueprints._
import scala.collection.JavaConversions._
import models.User

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
    sg.addType(User)

    g.shutdown()
  }
}
