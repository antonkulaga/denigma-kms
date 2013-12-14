package graphs

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.blueprints.Vertex
import scala.collection.JavaConversions._
import graphs.schemes.NodeType
import graphs.SG
import graphs.core.IndexedDB

//import scalax.collection.Graph // or

class SemanticGraph extends IndexedDB[Neo4jGraph]  {

  def init() = {
    val graph = new Neo4jGraph(url)
    initIndexes(graph)
    SG.sg = this
    graph

  }

  def root: Vertex  =  this.root(ROOT)

  def root(label:String,params:(String,String)*): Vertex  =  this.roots.get(ROOT,label).headOption match {
    case None=> val v: Vertex = addNode(params:_*)
      roots.put(ROOT,label,v)
      v
    case Some(v) =>setParams(v, params: _*)
  }

  def nodeType(str:String): Option[NodeType] = nodeTypeVertex(str) match {
    case None=>None
    case Some(v)=>NodeType.parse(v)
  }

  def nodeTypeVertex(str:String): Option[Vertex] = this.types.get(TYPE,str).headOption

  def addType(tp:NodeType,commit:Boolean = false) =   this.nodeType(tp.name) match {
      case None=>
        val t: Vertex = this.addNode(TYPE -> tp.name)
        t.setProperty(NAME,tp.name)
        tp.must.write(t)
        tp.should.write(t)
        if(commit)g.commit()

      case Some(ntp)=>
        if(tp!=ntp)
        {
          //TODO: add logger here
          val t: Vertex = this.addNode(TYPE -> tp.name)
          t.setProperty(NAME,tp.name)
          tp.must.write(t)
          tp.should.write(t)
          if(commit)g.commit()
        }
    }

//
//  def graphById(id: String) = {
//    val v = this.nodeById(id).get
//    val nv: NodeViewModel = new NodeViewModel(id,v)
//
//
//    val graph = Graph[NodeViewModel,EdgeViewModel]()
//
//
//  }





}
