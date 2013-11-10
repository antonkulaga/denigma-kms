package models

import models.graphs.SG
import com.tinkerpop.blueprints.Vertex
import scala.collection.JavaConversions._

import SG._

/*it creates the test graph*/
object TestGraph {


  val testNodeId =   "testRoot"
  val LIVE_IN = "LiveIn"
  val CREATED_BY="CreatedBy"
  val WORKS_FOR  ="WorksFor"
  val IS_LOCATED_IN = "IsLocatedIn"

  def init(): Vertex ={
    val c = SG.sg
    import c._

    val g = sg.g

    roots.get(ROOT, testNodeId).headOption match {
      case None =>
        val v: Vertex = this.createTestNodes()

        roots.put(ROOT, testNodeId, v)
        sg.g.commit()
        v
      case Some(v) => v
    }
  }

  def createTestNodes(): Vertex = {
    val c = SG.sg
    import c._

    val desc = "Thi is a root of TestGraph needed to test DenigmaKMS"
    val r = addNode(testNodeId,"name"->"Test root","description"->desc)

    val user = r ~> (CREATED_BY, "when" -> "test time")~>("name" -> "Test Master", "occupation" -> "I am test loard of this Realm!")
    val land = user ~> LIVE_IN ~>("name"->"Test Land","description"->"This is a land where all tests live")
    val testKingdom = user ~> WORKS_FOR ~>("name"->"Test Kingdom","description"->"Test Kingdom is organization created to test everything")
    testKingdom ~> IS_LOCATED_IN ~> land
    r
  }


}
