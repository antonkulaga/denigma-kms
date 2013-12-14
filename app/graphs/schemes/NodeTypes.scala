package graphs.schemes

import com.tinkerpop.blueprints.Vertex
import play.Logger
import graphs.SG
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
  def write(obj:Map[String,Any],commit:Boolean = true): Try[Vertex]
  def create(obj:Map[String,Any],commit:Boolean=true): Try[Vertex]
  def updateNode(obj:Map[String,Any],nd:Vertex,commit:Boolean=true): Try[Vertex]
}

class NodeType(val name:String) extends NodeTypeLike
{
  val should = new Schema()
  val must = new Schema()


  object NotValidVertex{
    def apply(check:Map[String,Option[Boolean]]) = {
      val rep = check.map(kv=>" { "+kv._1+" : "+kv._2.toString+" } ").foldLeft("")(_+_)
      Logger.error(s"not valid vertex params: "+rep)
      new NotValidVertex(check)
    }
  }
  class NotValidVertex(check:Map[String,Option[Boolean]]) extends Exception("object is not valid: "+check.toString())



  def write(obj:Map[String,Any],commit:Boolean = true): Try[Vertex] = obj.get("id") match {
    case Some(id)=>
      sg.nodeById(id.toString) match
      {
        case Some(nd)=>updateNode(obj,nd,commit)
        case None=> create(obj,commit)
      }
    case None=> create(obj,commit)
  }


  /* creates node with validation*/
  def create(obj:Map[String,Any],commit:Boolean=true): Try[Vertex] = {
    if (must.isValid(obj)) {
      val nd = sg.addNode(obj)
      //nd.addEdge(SG.TYPE,nd)
      if (commit) sg.g.commit()
      Success(nd)
    }
    else Failure(NotValidVertex(must.validate(obj)))
  }

  def updateNode(obj:Map[String,Any],nd:Vertex,commit:Boolean=true): Try[Vertex] = {
    val props = nd.propsWithNew(obj)
    if (must.isValid(props)) {
      sg.setParams(nd, props)
      //nd.addEdge(SG.TYPE,nd)
      if (commit) sg.g.commit()
      Success(nd)
    }
    else {
      Failure(NotValidVertex(must.validate(props)))
    }
  }

}

object NodeType {

  def parse(v:Vertex):Option[NodeType] = {

    val name = v.getProperty(NAME)
    if(name == null) {
      Logger.error(s"there is no type with $name")
      return None
    }
    val res = new NodeType(name)
    res.must.parse(v,MUST)
    res.should.parse(v,SHOULD)
    Some(res)
  }

}


