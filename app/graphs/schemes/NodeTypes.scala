package graphs.schemes

import com.tinkerpop.blueprints.Vertex
import play.Logger
import graphs.{Link, SG}
import SG._
import scala.util._
import graphs.core.GP
import graphs.schemes.constraints._


class LinkType(name:String) extends NodeType(name)
{
  must have StringOf(GP.LINK)

}

trait NodeTypeLike
{
  def save(obj:Map[String,Any]): Try[Vertex]
  def create(obj:Map[String,Any],links:Seq[Link]): Try[Vertex]
  def updateNode(obj:Map[String,Any],nd:Vertex, links:Seq[Link]): Try[Vertex]
}

/*
class that represents the type of a node, contains its schema and can do validation checks
 */
class NodeType(val name:String) extends NodeTypeLike
{
  val should = new Schema()
  val must = new Schema()


  object NotValidVertex{
    def apply(check:Map[String,Option[Boolean]]) = {
      val rep = check.map(kv=>" { "+kv._1+" : "+kv._2.toString+" } ").foldLeft("")(_+_)
      //Logger.error(s"not valid vertex params: "+rep)
      new NotValidVertex(check)
    }
  }
  class NotValidVertex(check:Map[String,Option[Boolean]]) extends Exception("object is not valid: "+check.toString())



  /*saves map of objects, splitting them into link and other objects
  TODO: write a macro to avoid manual splitting of properties and links
   */

  def save(obj:Map[String,Any]): Try[Vertex] = obj.span{
    case (key,value:Link)=>false
    case _=>true
  } match {
    case (props,links)=>save(props,links.values.map{case value:Link=>value}.toList)
  }

  /*
  saves map of properties together with maps of links
   */
  def save(props:Map[String,Any],links:Seq[Link]): Try[Vertex] = props.get("id").map{
    id=>
      sg.nodeById(id.toString).map(node=>updateNode(props,node,links))
        .getOrElse(create(props,links))
  }.getOrElse(create(props,links))

  /* creates node with validation*/
  def create(props:Map[String,Any],links:Seq[Link]): Try[Vertex] = {
    if (must.isValid("",props,links))
    {
      val nd = sg.addNode(props)
      //nd.addEdge(SG.TYPE,nd)
      sg.commit()
      Success(nd)
    }
    else Failure(NotValidVertex(must.validateProperties(props))) //TODO: validate links also
  }

  def updateNode(obj:Map[String,Any],nd:Vertex,links:Seq[Link]): Try[Vertex] = {
    import SG._
    val props = nd.propsWithNew(obj)
    if (must.isValid("",props,links)) {
      sg.setParams(nd, props)
      //nd.addEdge(SG.TYPE,nd)
      sg.commit()
      Success(nd)
    }
    else {
      Failure(NotValidVertex(must.validateProperties(props)))
    }
  }

}

object NodeType {

  /*
  parses a vertex to the type
   */
  def parse(v:Vertex) = Option {v.getProperty[String](NAME)}.map{name=>
    val res = new NodeType(name)
    res.must.parse(v,MUST)
    res.should.parse(v,SHOULD)
    res
  }.orElse{
    play.Logger.error(s"NoName type vertex")
    None
  }

}


