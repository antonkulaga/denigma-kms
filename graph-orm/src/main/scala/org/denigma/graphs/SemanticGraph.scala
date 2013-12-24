package org.denigma.graphs

import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import scala.collection.immutable.Map
import scala.util.Try
import org.denigma.graphs.core._
import org.denigma.graphs.schemes._
import scala.collection.JavaConversions._

//import scalax.collection.Graph // or

class SemanticGraph extends IndexedDB[Neo4jGraph]  {

  var allTypes = Map.empty[String,NodeType]

  def init() = {
    val graph = new Neo4jGraph(url)
    initIndexes(graph)
    SG.sg = this
    graph

  }

  def commit(str:String=""): Try[Unit] = {
    Try{
      g.commit()
    }.recoverWith{case e=>
      play.Logger.error("FAILED COMMIT "+ str+": "+e.toString)
       Try(g.rollback())
       }
  }


  def root: Vertex  =  this.root(ROOT)

  def root(label:String,params:(String,Any)*): Vertex  =  this.roots.get(ROOT,label).headOption match {
    case None=> val v: Vertex = addNode(params:_*)
      roots.put(ROOT,label,v)
      v
    case Some(v) =>setParams(v, params: _*)
  }

  def typeByName(str:String): Option[NodeType] = nodeTypeVertex(str) match {
    case None=>None
    case Some(v)=>NodeType.parse(v)
  }

  /*
  should return one type vertex by name
   */
  def nodeTypeVertex(str:String): Option[Vertex] = this.types.get(TYPE,str).headOption

  /*
  adds type to the database
   */
  def registerType(tp:NodeType,commit:Boolean = false) =   this.typeByName(tp.name) match {
      case None=>

        val t: Vertex = this.addNode(TYPE -> tp.name)
        t.setProperty(NAME,tp.name)
        tp.must.write(t)
        tp.should.write(t)
        if(commit)this.commit()
        play.Logger.info(s"TYPE '${tp.name}' added")
        this.allTypes += tp.name->tp

      case Some(ntp)=>
        if(tp!=ntp)
        {
          //TODO: add logger here
          val t: Vertex = this.addNode(TYPE -> tp.name)
          t.setProperty(NAME,tp.name)
          tp.must.write(t)
          tp.should.write(t)
          if(commit) this.commit(s"adding ${tp.name}type")
          play.Logger.info(s"type '${tp.name}' UPDATED")
        }
        this.allTypes += tp.name->tp

    }






}
