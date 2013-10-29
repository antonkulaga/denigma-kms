package models.graphs.constraints

import scala.collection.JavaConversions._
import play.Logger
import com.tinkerpop.blueprints._
import  scala.util.matching.Regex
import com.tinkerpop.blueprints.Vertex
import scala.collection.immutable._
import com.github.t3hnar.bcrypt._
import com.github.t3hnar.bcrypt
import models.graphs.SG._
import models.graphs.SG


trait Links
{
  var links = Map.empty[String,Link]
  def have(link:Link): Links = {links+=(link.name->link); this }

}

object Link
{
  import SG._
  val DIR = "direction"
  val BOTH = "both"->Direction.BOTH
  val IN = "in"->Direction.IN
  val OUT = "out"->Direction.OUT
  val LINK_TYPE = "link_type"
  val NODE_TYPE = "node_type"
  val NODE_ID = "node_id"
  val MIN_Q = "min_q"
  val MAX_Q = "max_q"


  def dir(v:Vertex) = v.getProperty[String](DIR) match{
    case null=>OUT._2
    case IN._1=>IN._2
    case BOTH._1=>BOTH._2
    case OUT._1=>OUT._2
  }

  def parse(v:Vertex):Option[Link] =  v.str(SG.NAME) match
  {
    case None=>None
    case Some(name)=>
      Some(new Link(
        name,
        dir(v),
        v.str(LINK_TYPE).getOrElse(""),
        v.str(NODE_TYPE).getOrElse(""),
        v.str(NODE_ID).getOrElse(""),
        v.int(MIN_Q).getOrElse(1),
        v.int(MAX_Q).getOrElse(Int.MaxValue)
      ))
  }

  def write(l:Link,v:Vertex) ={
    v.props(DIR->l.dir,NODE_TYPE->l.nodeType,NODE_ID->l.nodeId,LINK_TYPE->l.linkType,SG.NAME->l.name,MIN_Q->l.minQ,MAX_Q->l.maxQ)
  }
}

class Link(val name:String,val dir:Direction,val linkType:String,val nodeType:String,val  nodeId:String="", val minQ:Int=1, val maxQ:Int = Int.MaxValue)
{
//  val name:String
//  val dir:Direction
//  val linkType:String
//  val  nodeId:String
//  val nodeType:String

  def hasLink(v:Vertex) = this.edges(v).iterator().hasNext
  def edges(v:Vertex) = v.getEdges(dir,name)
  def vertices(v:Vertex) = v.getVertices(dir,name)


  def linkVertices(v:Vertex) = {
    vertices(v).filter(p=>p.isLink)
  }

  def isOfType(v:Vertex) = v.getEdges(dir, TYPE).iterator().hasNext

//  def isRightNode(v:Vertex)= ()
//
//  def linkOfType
}

case class OutLinkOf(lname:String,lType:String="",nType:String="") extends Link(lname, Direction.OUT,lType,nType)
case class InLinkOf(lname:String,lType:String="",nType:String="") extends Link(lname, Direction.IN,lType,nType)

//case class LinkToNodeOf(linkName:String,lDir:Direction,nType:String) extends Link(linkName,lDir) {
//  val  nodeId:String   = ""
//  val linkType:String
//  val nodeType = nType
//}
