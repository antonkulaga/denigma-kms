package graphs.schemes

import graphs.{Link, SG}
import SG._
import com.tinkerpop.blueprints._
import scala.Predef._
import scala.collection.JavaConversions._
import scala.collection.immutable.Map


trait Links
{
  var linkOfs = Map.empty[String,LinkOf]
  def have(link:LinkOf): Links = {linkOfs+=(link.label->link); this }
  def be(link:OutLinkOf): LinkOf = {linkOfs+=(link.label->link);link}
  def be(link:InLinkOf): LinkOf = {linkOfs+=(link.label->link);link}

  def linksValid(links:Seq[Link],v:Vertex):Boolean = linksValid(links,v)

  /*
    check if object links are valid
   */
  def linksValid(links:Seq[Link],vid:String):Boolean = linkOfs.forall{
    case (key,lof)=>
      links.exists(l=>lof.isValid(l,vid))
  }

  //TODO: add validate!

 
  def writeLinkOfs(v:Vertex): Vertex =  {this.linkOfs.foreach
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

/*
Companion object for LinkOf constrains with some useful constants and methods
 */
object LinkOf
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


  /*
  extracts direction from linkvertex
   */
  def dir(v:Vertex) = v.getProperty[String](DIR) match{
    case null=>OUT._2
    case IN._1=>IN._2
    case BOTH._1=>BOTH._2
    case OUT._1=>OUT._2
  }

  /*
  Parses the typevertex to find all linkof constrains
   */
  def parse(typeVertex:Vertex):Option[LinkOf] =  typeVertex.str(SG.NAME) match
  {
    case None=>None
    case Some(name)=>
      Some(new LinkOf(
        name,
        dir = dir(typeVertex),
        linkType = typeVertex.str(LINK_TYPE).getOrElse(""),
        nodeType = typeVertex.str(NODE_TYPE).getOrElse(""),
        nodeId = typeVertex.str(NODE_ID).getOrElse(""),
        minQ = typeVertex.int(MIN_Q).getOrElse(1),
        maxQ = typeVertex.int(MAX_Q).getOrElse(Int.MaxValue),
        caption = typeVertex.str(CAPTION).getOrElse("")

      ))
  }

  /*
  writes linkof constrains to the typevertex
   */
  def write(l:LinkOf,typeVertex:Vertex): Unit ={
    typeVertex.props(DIR->l.dir.toString,NODE_TYPE->l.nodeType,NODE_ID->l.nodeId,LINK_TYPE->l.linkType,SG.NAME->l.label,MIN_Q->l.minQ,MAX_Q->l.maxQ,CAPTION->l.caption)
  }
}

/*
This is class of a link constraint
 */
class LinkOf(val label:String,val dir:Direction,val linkType:String,val nodeType:String,val  nodeId:String="", var minQ:Int=1, var maxQ:Int = Int.MaxValue, val caption:String="")
{

  def hasLink(v:Vertex) = this.edges(v).iterator().hasNext
  def edges(v:Vertex) = v.getEdges(dir,label)
  def vertices(v:Vertex) = v.getVertices(dir,label)

  def title = if(caption=="") label else caption


  def linkVertices(v:Vertex) = {
    vertices(v).filter(p=>p.isLink)
  }
  def isOfType(v:Vertex): Boolean = v.getEdges(dir, TYPE).iterator().hasNext


  def exactNode = nodeId!=""
  def exactNodeType = nodeType!=""
  def hasType = linkType!=""



  def isValid(link:Link,v:Vertex) = link.label==label &&  isDirectedRight(link,v) && fitsLinkType(link) && fitsNodeType(link)
  def isValid(link:Link,vid:String) =link.label==label &&  isDirectedRight(link,vid) && fitsLinkType(link) && fitsNodeType(link)

  def write(v:Vertex) ={
    LinkOf.write(this,v)
  }
  /*
  this vertex
   */
  protected def vId(link:Link): String = if(dir==Direction.OUT) link.from else link.to
  /*
  not this vertex
   */
  protected def nvId(link:Link): String = if(dir==Direction.OUT) link.to else link.from

  /*
  Checks of link is directed right
   */
  def isDirectedRight(link:Link,vid:String): Boolean =  vId(link) == vid
  def isDirectedRight(link:Link,v:Vertex): Boolean =  isDirectedRight(link,v.id)

  protected def linkV(link:Link,v:Vertex): Option[Vertex] = v.getVertices(dir,link.label).find(p=>link.hasVertex || v.id==vId(link))



  /*
  Checks if a link fits type
   */
  def fitsLinkType(link:Link): Boolean = (linkType=="") || (link.types.contains(linkType)
    && sg.typeByName(linkType).exists(nd=>nd.must.propsAreValid(link.props)))

  /*
  Checks if a link fits type
   */
  def fitsNodeType(link:Link) =  !exactNodeType || {
    sg.nodeById(nvId(link)) match {
      case None=>false
      case Some(nv)=>nv.isOfType(nodeType)
    }

  }

}

case class OutLinkOf(linkName:String,typeOfLink:String="",typeOfNode:String="", cap:String="") extends LinkOf(linkName, Direction.OUT,typeOfLink,typeOfNode,cap)
case class InLinkOf(linkName:String,typeOfLink:String="",typeOfNode:String="", cap:String="") extends LinkOf(linkName, Direction.IN,typeOfLink,typeOfNode,cap)
