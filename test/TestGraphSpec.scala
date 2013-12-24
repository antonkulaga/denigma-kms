
import models.TestGraph
import org.denigma.graphs.core.GP
import org.denigma.graphs.{SG, SemanticGraph}
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import play.api.test._
import scala.collection.JavaConversions._
import SG._

@RunWith(classOf[JUnitRunner])
class TestGraphSpec extends SemanticSpec
{
  "TestGraph" should {
    val tg = TestGraph
    def prepareTest = {
      val sg: SemanticGraph = prepareGraph
      tg.init()
      sg.g.commit()
      sg
    }

    "have all relationships" in new WithApplication {
       val sg  = prepareTest
       val r = sg.root(tg.testNodeId)
       r.str("name") shouldEqual(Some("Test root"))
       r.str("description") shouldEqual (Some("Thi is a root of TestGraph needed to test DenigmaKMS"))
       val ov = r.outV(tg.CREATED_BY).toList
       ov.size shouldEqual(1)
       ov.head.isLink must beTrue
       ov.head.isLink(tg.CREATED_BY) must beTrue
       val c = r.outL(tg.CREATED_BY).toList
       c.size shouldEqual 1

       val cr= c.head
       cr.str("when") shouldEqual Some("test time")
       cr.isLink(tg.CREATED_BY) must beTrue

       val ul = cr.outV(tg.CREATED_BY).toList
       ul.size shouldEqual 1

       val rm = ul.head
       r.outL(tg.CREATED_BY).head.outV(tg.CREATED_BY).size.shouldEqual(1)

       //val rml = cr.outLV(tg.CREATED_BY).toList
       //rml.size shouldEqual(1)

       //val rm2 = rml.head
       rm.str("name") shouldEqual Some("Test Master")
       //rm.str("name") shouldEqual rm2.str("name")
       //rm.str("occupation") shouldEqual rm2.str("occupation")
       rm.str("occupation") shouldEqual Some("I am test loard of this Realm!")
       val wr = rm.outL(tg.WORKS_FOR).head
       val tk = wr.outV(tg.WORKS_FOR).head
       tk.str("description") shouldEqual Some("Test Kingdom is organization created to test everything")


      sg.g.shutdown()

    }

    "work with indexes an IN nodes and vertices" in new WithApplication {
      val sg = prepareTest
      val r = sg.root(tg.testNodeId)
      val ll = sg.names.get(GP.NAME, "Test Land").toList.headOption
      ll must not beNone
      val land = ll.get
      land.str("name") shouldEqual Some("Test Land")
      //       val il = land.inL().toList
      //       il.size.shouldEqual(2)
      val ill = land.inL(tg.LIVE_IN)
      ill.size shouldEqual 1
      val inmate = ill.head.inV(tg.LIVE_IN).head
      inmate.str("name") shouldEqual Some("Test Master")
      //rm.str("name") shouldEqual rm2.str("name")
      //rm.str("occupation") shouldEqual rm2.str("occupation")
      inmate.str("occupation") shouldEqual Some("I am test loard of this Realm!")



      sg.g.shutdown()
    }

    "have vIn and vOut that work well" in new WithApplication{

      val sg = prepareTest
      val r = sg.root(tg.testNodeId)

      import SG._
      import tg._

      val tm = sg.names.get(GP.NAME, "Test Master").toList.headOption
      tm must not beNone
      val user = tm.get

      //      val all = user.allV
      val incoming = user.allInV
      val outgoing = user.allOutV
  //    all.size shouldEqual 3
      incoming.size shouldEqual 1
      outgoing.size shouldEqual 2
    //  all should containAllOf(List(LIVE_IN,WORKS_FOR,CREATED_BY))
      incoming.map(_._1) should containAllOf(List(CREATED_BY))
      outgoing.map(_._1) should containAllOf(List(LIVE_IN,WORKS_FOR))

    }


  }

}
