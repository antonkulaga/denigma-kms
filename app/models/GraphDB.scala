package models

import play.api.Play
import play.api.Play.current

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph


;


//object GraphDB extends GraphDB


/**
 * Graph database singletone
 */
class GraphDB {

  /*gets or creates index*/

//
//  def tryIndex(key:String): Index[Vertex] = tryIndex(key,g)
//
//
//
//  def tryIndex(key:String, db:TitanGraph): Index[Vertex] =  db.getIndex(key,classOf[Vertex]) match
//    {
//      case null=> db.createIndex(key,classOf[Vertex])
//      case ind:Index[Vertex]=>ind
//    }




  lazy val g = init()

  def init() = {
    //val graph = new OrientGraph(url)
    val graph = new Neo4jGraph(url)
    graph

  }




//  lazy val url:String = if(Play.isTest) Play.current.configuration.getString("orientdb.test.url").get
//    else Play.current.configuration.getString("orientdb.url").get
  lazy val url:String = if(Play.isTest) Play.current.configuration.getString("graph.test.url").get
    else Play.current.configuration.getString("grapht.url").get



  def cleanByKey[T](key:String,value:T) =   this.nodesByKey(key,value).foreach(v=>g.removeVertex(v))

  def nodesByKey[T](key:String,value:T) = g.getVertices(key,value).iterator().toList
  //lazy val db:TitanGraph = new TitanGraph("memory:tinkerpop")




}
