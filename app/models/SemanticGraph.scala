package models

import com.tinkerpop.blueprints._
import java.lang.RuntimeException
import scala.Some
import java.io.IOException
import java.io.File
import org.apache.commons.io._

import java.util.UUID
import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import org.neo4j.index.impl.lucene.LowerCaseKeywordAnalyzer

//import com.tinkerpop.blueprints.impls.orient.{OrientVertex, OrientGraph}


object SG extends SemanticGraph
/**
 * class for semantic Graph features
 */
class SemanticGraph extends GraphDB{


  val sys = "__"

  val ID = sys+"id"
  val TYPE = sys+"type"
  val CONTEXT = sys+"context"

  val MUST =sys+"must"
  val SHOULD = sys+"should"
  val VERSION = sys+"version"
  val USERNAME = sys+"username"
  val EMAIL = sys+"email"
  val PASSWORD = sys+"password"
  val SALT = sys+"salt"
  val AUTH = sys+"auth"




//  lazy val ids = g.getRawGraph.index().forNodes( "ids")
//  lazy val names = g.getRawGraph.index().forNodes( "names")
//  lazy val users = g.getRawGraph.index().forNodes( "users")
//  lazy val types = g.getRawGraph.index().forNodes( "types")

  private lazy val lowerCaseAnalyzer  = classOf[LowerCaseKeywordAnalyzer].getName


  def index(name:String,fulltext:Boolean,graph:Neo4jGraph /*OrientGraph*/): Index[Vertex] = {
    var ind: Index[Vertex] = graph.getIndex(name,classOf[Vertex])
    if(ind==null)
    {
      ind = if(fulltext) addFullTextIndex(name) else addIndex(name)
      indexes = indexes + (ind.getIndexName -> ind)
    }
    ind
  }

  def index(name:String,fulltext:Boolean=true): Index[Vertex] = index(name,fulltext,g)

  def addIndex(name:String) =  g.createIndex(name, classOf[Vertex] ,new Parameter("analyzer", lowerCaseAnalyzer))
  def addFullTextIndex(name:String) =  g.createIndex(name, classOf[Vertex],new Parameter("analyzer", lowerCaseAnalyzer),new Parameter("type","fulltext"))

  var indexes = Map.empty[String,Index[Vertex]]
  //lazy val ofType = g.getRawGraph.index().forRelationships("ofType")

  var ids: Index[Vertex] = null
  var names: Index[Vertex] = null
  var users: Index[Vertex] = null
  var types: Index[Vertex] = null

  def initIndexes() = {
    ids = index("id")
    names = index("name")
    users = index("user")
    types = index("type")

  }




  def clearLocalDb()= {
    FileUtils.cleanDirectory(new File(url))

//    try {
//      FileUtils.cleanDirectory(new File(url))
//    }
//    catch {
//      case e: IOException => {
//        throw new RuntimeException(e)
//      }
//    }
  }

  /*
    Adds indexable params
   */
  def addNode(params:(String,String)*): Vertex =
  {

    val v = g.addVertex(null)
    val id = UUID.randomUUID()
    v.setProperty(ID,UUID.randomUUID().toString)
    ids.put(ID,id,v)
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

  /*
* class that add some new features to Vertex
* */
  implicit class SemanticNode(v:Vertex){

  val ofType = "ofType"


  def setInd(ind:Index[Vertex],name:String,value:String) = {

    v.getProperty[String](name) match {
      case null => //skip
      case str:String =>ind.remove(name,value,v)
    }

    v.setProperty(name,value)
    ind.put(name,value,v)
    v
  }

  def setName(name:String) = setInd(names,"name",name)
  def setTypes(name:String) = setInd(types,"types",name)
  def setUsers(name:String) = setInd(users,"users",name)

  }
}

