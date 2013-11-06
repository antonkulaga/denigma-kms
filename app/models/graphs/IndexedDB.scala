package models.graphs

import play.api.Play
import play.api.Play.current

import org.neo4j.index.impl.lucene.LowerCaseKeywordAnalyzer
import com.tinkerpop.blueprints._
import org.apache.commons.io.FileUtils
import java.io.File
import java.util.UUID
import scala.collection.JavaConversions._
import scala.None


/*
abstract class for graph databases with Indexes
 */
abstract class IndexedDB[T<: IndexableGraph] extends GraphDB[T] with DefIndexes[T]{

  protected lazy val lowerCaseAnalyzer  = classOf[LowerCaseKeywordAnalyzer].getName

  def index(name:String,fulltext:Boolean=true): Index[Vertex] = index(name,fulltext,g)

  def addIndex(name:String): Index[Vertex] =  addIndex(name,g)

  def addIndex(name:String,graph:T): Index[Vertex] =  graph.createIndex(name, classOf[Vertex] ,new Parameter("analyzer", lowerCaseAnalyzer))

  def addFullTextIndex(name:String): Index[Vertex] =  addFullTextIndex(name:String, g)

  def addFullTextIndex(name:String,graph:T): Index[Vertex] =  graph.createIndex(name, classOf[Vertex],new Parameter("analyzer", lowerCaseAnalyzer),new Parameter("type","fulltext"))


  def index(name:String,fulltext:Boolean,graph:T): Index[Vertex] = {
    var ind: Index[Vertex] = graph.getIndex(name,classOf[Vertex])
    if(ind==null)
    {
      ind = if(fulltext) addFullTextIndex(name,graph) else addIndex(name,graph)
      indexes = indexes + (ind.getIndexName -> ind)
    }
    ind
  }


  var indexes: Map[String, Index[Vertex]] = Map.empty[String,Index[Vertex]]

  def nodeById(id:String): Option[Vertex] = ids.get(GP.ID,id).headOption

  def nodeByName(name: String): Option[Vertex] = names.get(GP.NAME, name).headOption


  def addNode(params:(String,String)*):Vertex = this.addNode(UUID.randomUUID().toString,params:_*)

  def addNode(id:String,params: (String, String)*): Vertex = {

    val v = g.addVertex(null)
    v.setProperty(ID, id)
    ids.put(ID, id, v)
    setParams(v, params: _*)
  }


  def setParams(v:Vertex,params:(String,String)*): Vertex =   {
    params.foreach{
      case (key,value)=>
        v.setProperty(key,value)
        indexes.get(key)  match
        {
          case Some(ind: Index[Vertex])=>  ind.put(key,value,v)
          case _ =>
        }
    }
    //g.commit()
    v
  }

}
