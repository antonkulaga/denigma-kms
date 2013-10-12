import models.graphs.{SG, SemanticGraph}
import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._

import play.api.test._
import play.Play
import com.tinkerpop.blueprints._
import scala.collection.JavaConversions._


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
@RunWith(classOf[JUnitRunner])
class SchemaSpec extends SemanticSpec {




  "Graph schema" should {



    "add link" in new WithApplication{
      Play.application().isTest must beTrue
      val sg = new SemanticGraph()

      sg.url must contain ("/tmp/db/test")
      sg.clearLocalDb()
      val g = sg.g
      import sg._
      val root = sg.root(sg.ROOT,"name"->sg.ROOT,"other"->"otherval")
      val learn = "learn"
      val manage = "manage"
      val discover="discover"
      g.commit()
      sg.g must not beNull
      val l: Vertex = root.addLink(learn,"name"->learn)
      val people = sg.addNode("name"->"people")
      val m = root.addLinkTo(manage,people,"name"->manage)
      val discoveries = sg.addNode("name"->"discoveries")
      val d = root.addGetLinkTo(discover,discoveries,"name"->discover)
      val dd  =root.addGetLinkTo(discover,discoveries)
      g.commit()
      val rr = sg.root
      rr.getProperty[String]("other") must beEqualTo("otherval")
      val learnL = rr.getVertices(Direction.OUT,learn).toList
      learnL.size must beEqualTo(1)
      val learnT = learnL.head
      learnT.getProperty[String]("name") must beEqualTo(learn)
      learnT.isLink must beTrue
      learnT.isLink(manage) must beFalse
      learnT.isLink(learn) must beTrue

    }


    "get type of the node" in new WithApplication{
       val sg = prepareGraph


    }

    "get as link" in new WithApplication{
      val sg = prepareGraph



    }


  }

}
