package models.graphs

import play.api.Play
import play.api.Play.current

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import org.neo4j.index.impl.lucene.LowerCaseKeywordAnalyzer
import com.tinkerpop.blueprints._
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.UUID
import scala.Some

/**
 * Graph database
 * TODO:change strange inheritance
 */
abstract class GraphDB[TG<: IndexableGraph]{

  lazy val g: TG = init()
  protected def init():TG




  def addNode(params:(String,String)*):Vertex
  def nodeById(id:String): Option[Vertex]
  def nodes[TV](key:String,value:TV): List[Vertex] = g.getVertices(key,value).toList

  def clearLocalDb()=  FileUtils.cleanDirectory(new File(url))



  //  lazy val url:String = if(Play.isTest) Play.current.configuration.getString("orientdb.test.url").get
  //    else Play.current.configuration.getString("orientdb.url").get
  lazy val url:String = if(Play.isTest)
    Play.current.configuration.getString("graph.test.url").get
    else Play.current.configuration.getString("grapht.url").get


  def cleanByKey[TV](key:String,value:TV): Unit =   this.nodes(key,value).foreach(v=>g.removeVertex(v))


}
