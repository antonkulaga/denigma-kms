package graphs.schemes

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints._
import com.tinkerpop.blueprints.Vertex
import scala.collection.immutable._
import graphs.SG._
import graphs.SG


trait Links
{
  var links = Map.empty[String,Link]
  def have(link:Link): Links = {links+=(link.name->link); this }
  def be(link:OutLinkOf): Link = {links+=(link.name->link);link}
  def be(link:InLinkOf): Link = {links+=(link.name->link);link}


  def writeLinks(v:Vertex): Vertex =  {this.links.foreach
  {

    case (name,link)=>
      v.outV(CONSTRAINT).find(_.has("name",name)) match
      {
        case None=>link.write(v.addConnected(CONSTRAINT))
        case Some(vt)=>link.write(vt)
      }

  }
    v}
}

object Link
{

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
        dir = dir(v),
        linkType = v.str(LINK_TYPE).getOrElse(""),
        nodeType = v.str(NODE_TYPE).getOrElse(""),
        nodeId = v.str(NODE_ID).getOrElse(""),
        minQ = v.int(MIN_Q).getOrElse(1),
        maxQ = v.int(MAX_Q).getOrElse(Int.MaxValue),
        caption = v.str(CAPTION).getOrElse("")

      ))
  }

  def write(l:Link,v:Vertex) ={
    v.props(DIR->l.dir.toString,NODE_TYPE->l.nodeType,NODE_ID->l.nodeId,LINK_TYPE->l.linkType,SG.NAME->l.name,MIN_Q->l.minQ,MAX_Q->l.maxQ,CAPTION->l.caption)
  }
}

/*
This is class of a link constraint
 */
class Link(val name:String,val dir:Direction,val linkType:String,val nodeType:String,val  nodeId:String="", var minQ:Int=1, var maxQ:Int = Int.MaxValue, val caption:String="")
{

  def hasLink(v:Vertex) = this.edges(v).iterator().hasNext
  def edges(v:Vertex) = v.getEdges(dir,name)
  def vertices(v:Vertex) = v.getVertices(dir,name)

  def title = if(caption=="") name else caption


  def linkVertices(v:Vertex) = {
    vertices(v).filter(p=>p.isLink)
  }

  def isOfType(v:Vertex) = v.getEdges(dir, TYPE).iterator().hasNext

  def write(v:Vertex) ={
    Link.write(this,v)
  }

}

case class OutLinkOf(linkName:String,typeOfLink:String="",typeOfNode:String="", cap:String="") extends Link(linkName, Direction.OUT,typeOfLink,typeOfNode,cap)
case class InLinkOf(linkName:String,typeOfLink:String="",typeOfNode:String="", cap:String="") extends Link(linkName, Direction.IN,typeOfLink,typeOfNode,cap)

//case class LinkToNodeOf(linkName:String,lDir:Direction,nType:String) extends Link(linkName,lDir) {
//  val  nodeId:String   = ""
//  val linkType:String
//  val nodeType = nType
//}
