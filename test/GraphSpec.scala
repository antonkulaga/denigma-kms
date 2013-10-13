import models.graphs.SemanticGraph
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
class GraphSpec extends SemanticSpec {

  "Graph" should {

    "Test add Node" in new WithApplication{
      val sg = prepareGraph

      //sg.url must contain ("plocal:./db/test")
      val g = sg.g
      //sg.initIndexes()
      val names = sg.names
      val ids = sg.ids
      val types = sg.types
      val v1 = sg.addNode("name"->"first")

      val v2 = sg.addNode("name"->"second")

      val e = v2.addEdge("son",v1)
      e.setProperty("name","sonEdge")
      g.commit()

      g.query().has("name","some").vertices().iterator().hasNext must beFalse

      g.query().has("name","second").vertices().iterator().hasNext must beTrue
      val iter = g.query().has("name","first").vertices().iterator()
      iter.hasNext() must beTrue
      val vf = iter.next()
      vf.getEdges(Direction.OUT,"son").iterator().hasNext must beFalse
      val ie = vf.getEdges(Direction.IN,"son").iterator()
      ie.hasNext must beTrue
      val ee = ie.next()
      ee.getLabel() must beEqualTo("son")
      ee.getProperty[String]("name") must beEqualTo("sonEdge")
      val vs = ee.getVertex(Direction.OUT)
      vs.getProperty[String]("name") must beEqualTo("second")

      sg.indexes.contains("name") must beTrue

      sg.names.query("name","some").iterator().hasNext must beFalse
      sg.names.query("name","second").iterator().hasNext must beTrue
      sg.names.query("name","first").iterator().hasNext must beTrue

      sg.names.query("name","*con*").iterator().hasNext must beTrue
      sg.names.query("name","*can*").iterator().hasNext must beFalse

      g.shutdown()
    }

    "Test index search" in new WithApplication{
      Play.application().isTest must beTrue
      val sg = prepareGraph
      val g = sg.g
      //sg.initIndexes()
      //try {
        sg.addNode("name"->"Alexa")
        sg.addNode("name"->"Alexey")
        sg.addNode("name"->"Alexandr")
        sg.addNode("name"->"Alexandra")
        sg.addNode("name"->"Artem")
        sg.addNode("name"->"Andrey")
        sg.addNode("name"->"Anton")
        sg.addNode("name"->"Anatoliy")
        sg.addNode("name"->"Anderson")
        g.commit()
      g.query().has("name").vertices().toList.size must beEqualTo(9)
      g.query().has("name","Anton").vertices().toList.size must beEqualTo(1)
      g.query().has("name","Alexandr").vertices().toList.size must beEqualTo(1)
      g.query().has("name",Query.Compare.EQUAL,"Alexandr").vertices().toList.size must beEqualTo(1)
      //g.query().has("name",Query.Compare.NOT_EQUAL,"Alexandr").vertices().toList.size must beEqualTo(8)
      sg.names.query("name","Alexandr*").toList.size must beEqualTo(2)
      sg.names.query("name","Alexandra*").toList.size must beEqualTo(1)
      sg.names.query("name","Alexandra*").toList.size must beEqualTo(1)
      //g.query().has("name","*lex*").vertices().toList.size must beEqualTo(5)
      sg.names.query("name","*to*").toList.size must beEqualTo(2)
      sg.names.query("name","*ton").toList.size must beEqualTo(1)
      g.shutdown()
    }
  }
}
