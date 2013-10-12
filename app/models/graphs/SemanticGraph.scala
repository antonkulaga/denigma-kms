package models.graphs

import com.tinkerpop.blueprints._
import scala.{collection, Some}

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import scala.collection.JavaConversions._
import play.Logger

//import com.tinkerpop.blueprints.impls.orient.{OrientVertex, OrientGraph}

/**
 * class for semantic Graph features
 */

class SemanticGraph extends IndexedDB[Neo4jGraph] {
  val CONTEXT = sys+"context"
  val MUST =sys+"must"
  val SHOULD = sys+"should"
  val VERSION = sys+"version"

  val PROPERTY = sys+"property"
  val LINK = sys+"link"

  protected def init() = {
    val graph = new Neo4jGraph(url)
    initIndexes(graph)
    graph

  }

  def root: Vertex  =  this.root(ROOT)

  def root(label:String,params:(String,String)*): Vertex  =  this.roots.get(ROOT,label).headOption match {
    case None=> val v: Vertex = addNode(params:_*)
      roots.put(ROOT,label,v)
      v
    case Some(v) =>v
  }


  /*
* class that add some new features to Vertex
* */
  implicit class SemanticNode(v:Vertex)
  {

    def setInd(ind:Index[Vertex],name:String,value:String): Vertex = {

    v.getProperty[String](name) match {
      case null => //skip
      case str:String =>ind.remove(name,value,v)
    }

    v.setProperty(name,value)
    ind.put(name,value,v)
    v
  }

  def linkVertices(dir:Direction,labels:String): collection.Iterable[Vertex] = v.getVertices(dir,labels).filter(p=>p.isLink)   //TODO: rewrite

  /*
  adds vertex as link
   */
  def addLink(label:String, params:(String,String)*): Vertex = {
    val n=   addNode(params:_*).asLink(label)
    v.addEdge(label,n)
    n
  }

  def addGetLink(label:String, params:(String,String)*): Vertex =  {
    val n= setParams(getLinkNode(label).getOrElse(addNode()))
    v.addEdge(label,n)
    n
  }

  def addGetLinkTo(label:String, to:Vertex,params:(String,String)*): Vertex=  {
    val n= this.addGetLink(label,params:_*)
    n.addEdge(label,to)
    n
  }

  def addLinkTo(label:String, to:Vertex, params:(String,String)*): Vertex = {
    val n=   this.addLink(label,params:_*)
    n.addEdge(label,to)
    n
  }

  def getLinkNode(label:String): Option[Vertex] = v.getVertices(Direction.OUT, label).find(p => p.isLink(label))


  def getSetLinkNode(label:String, params:(String,String)*): Vertex =  setParams(v.getLinkNode(label).getOrElse(addNode().asLink(label)))



  def setName(name:String): Vertex = setInd(names,"name",name)
  def setTypes(name:String): Vertex = setInd(types,"type",name)
  def setUsers(name:String): Vertex = setInd(users,"user",name)


  /*check if it is link of label type*/
  def isLink(label:String): Boolean = this.Link.getOrElse("")==label

  def isLink: Boolean = v.getProperty[String](LINK) match
  {
    case null=>false
    //case false=>false
    case _=>true
  }

  def asLink(label:String): Vertex = {
    v.setProperty(LINK,label)
    v
  }

  def Link:Option[String] = v.getProperty[String](LINK) match {
    case null=>None
    case str:String =>Some(str)
    case _ => Logger.error("Strange link inside") ; None
  }

  def isProperty: Boolean = v.getProperty(PROPERTY)!=null

}


}
