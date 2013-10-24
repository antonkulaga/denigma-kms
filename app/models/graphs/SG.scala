package models.graphs

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph

import com.tinkerpop.blueprints._
import scala.{collection, Some}

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph
import scala.collection.JavaConversions._
import play.Logger
import java.lang
import models.graphs.constraints.NodeType

//import com.tinkerpop.blueprints.impls.orient.{OrientVertex, OrientGraph}

/**
 * class for semantic Graph features
 */


object SG extends GraphParams
{

  var sg:SemanticGraph = _
  /*
* class that add some new features to Vertex
* */
  implicit class SemanticNode(v:Vertex)
  {

    def strings(params:String*): Map[String, String] = props[String](params:_*)
    def doubles(params:String*): Map[String, Double] = props[Double](params:_*)
    def ints(params:String*): Map[String, Int] = props[Int](params:_*)


    protected def props[T](params:String*): Map[String, T] = params.map(p=>
      v.getProperty[T](p) match
      {
        case value:T=>p->value
      }).toMap

    def p[T](label:String): Option[T] = v.getProperty[T](label) match {case null=>None; case value:T=>Some(value)}
    def str(name:String): Option[String] = p[String](name)
    def double(name:String): Option[Double] = p[Double](name)
    def int(name:String): Option[Int] = p[Int](name)
    def long(name:String): Option[Long] = p[Long](name)

    def bool(name:String): Option[Boolean] = p[Boolean](name)



    def inV(labels:String*): lang.Iterable[Vertex] = v.getVertices(Direction.IN,labels:_*)
    def inE(labels:String*): lang.Iterable[Edge] = v.getEdges(Direction.IN,labels:_*)
    def inL(labels:String*): Iterable[Vertex] = v.getVertices(Direction.IN,labels:_*).filter(v=>v.isLink(labels:_*))

    def outV(labels:String*): lang.Iterable[Vertex] = v.getVertices(Direction.OUT,labels:_*)
    def outE(labels:String*): lang.Iterable[Edge] = v.getEdges(Direction.OUT,labels:_*)
    def outL(labels:String*): Iterable[Vertex] = v.getVertices(Direction.OUT,labels:_*).filter(v=>v.isLink(labels:_*))

    def setInd(ind:Index[Vertex],name:String,value:String): Vertex = {

      v.getProperty[String](name) match {
        case null => //skip
        case str:String =>ind.remove(name,value,v)
      }

      v.setProperty(name,value)
      ind.put(name,value,v)
      v
    }

    /*get vertices marked as links*/
    def linkVertices(dir:Direction,labels:String): collection.Iterable[Vertex] = v.getVertices(dir,labels).filter(p=>p.isLink)   //TODO: rewrite


    /* adds connected node*/
    def addConnected(label:String, params:(String,String)*): Vertex = {
      val n=   sg.addNode(params:_*)
      v.addEdge(label,n)
      n
    }
    /* adds or gets connected node*/
    def addGetConnected(label:String, params:(String,String)*): Vertex =  {
      val n= sg.setParams(v.getVertices(Direction.OUT, label).headOption.getOrElse(sg.addNode()))
      v.addEdge(label,n)
      n
    }

    /*
    adds vertex as link
     */
    def addLink(label:String, params:(String,String)*): Vertex = addConnected(label,params:_*).toLink(label)


    def addGetLink(label:String, params:(String,String)*): Vertex =  addGetConnected(label,params:_*).toLink(label)

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

    def getSetLinkNode(label:String, params:(String,String)*): Vertex =  sg.setParams(v.getLinkNode(label).getOrElse(sg.addNode().toLink(label)))

    def nodeTypes = v.getVertices(Direction.OUT, TYPE)

    def nodeType(name:String) = nodeTypes.find(_.getProperty(TYPE)==name)

    def isOfType(name:String) = nodeType(name) == None




    def setName(name:String): Vertex = setInd(sg.names,"name",name)
    def setTypes(name:String): Vertex = setInd(sg.types,"type",name)
    def setUsers(name:String): Vertex = setInd(sg.users,"user",name)


    /*check if it is link of label type*/
    def isLink(labels:String*): Boolean = this.asLink match {
      case Some(l)=>labels.contains(l)
      case None =>false
    }

    def isLink: Boolean = v.getProperty[String](LINK) match
    {
      case null=>false
      //case false=>false
      case _=>true
    }

    def toLink(label:String): Vertex = {
      //labels.foreach(v.setProperty(LINK,_))
      v.setProperty(LINK,label)
      v
    }

    def asLink:Option[String] = v.getProperty[String](LINK) match {
      case null=>None
      case str:String =>Some(str)
      case _ => Logger.error("Strange link inside") ; None
    }

    def isProperty: Boolean = v.getProperty(PROPERTY)!=null

  }
}
