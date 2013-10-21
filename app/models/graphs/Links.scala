package models.graphs

import scala.collection.JavaConversions._
import play.Logger
import com.tinkerpop.blueprints._
import  scala.util.matching.Regex
import SG.SemanticNode
import com.tinkerpop.blueprints.Vertex
import scala.collection.immutable._
import com.github.t3hnar.bcrypt._
import com.github.t3hnar.bcrypt



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
    vertices(v).filter(p=>p.isLink())
  }

  def isOfType(v:Vertex) = v.getEdges(dir, SG.TYPE).iterator().hasNext

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
