package models.graphs

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import com.tinkerpop.blueprints.{Vertex, Graph}
import models.graphs.constraints.NodeType
import scala.collection.JavaConversions._


class SemanticGraph extends IndexedDB[Neo4jGraph]  {

  protected def init() = {
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
    case Some(v) =>v
  }

  def nodeType(str:String): Option[NodeType] = nodeTypeVertex(str) match {
    case None=>None
    case Some(v)=>NodeType.parse(v)
  }

  def nodeTypeVertex(str:String): Option[Vertex] = this.types.get(TYPE,str).headOption

  def addType(tp:NodeType,commit:Boolean = false) =   this.nodeType(tp.name) match {
      case None=>
        val t = this.addNode((TYPE->tp.name))
        //tp.must.
        if(commit)g.commit()

      case Some(tp)=>
        if(commit)g.commit()
    }





}
