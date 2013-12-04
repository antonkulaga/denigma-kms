package models.graphs.constraints

import models.graphs.{SG, SemanticGraph, GP}
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import play.Logger
import scala.collection.JavaConversions._
import SG._
import models.graphs.constraints.StringOf
import scala.collection.IterableView
import scala.util._


class LinkType(name:String) extends NodeType(name)
{
  must have StringOf(GP.LINK)

}

class NodeType(val name:String)
{
  val should = new Schema()
  val must = new Schema()

  class NotValidVertex(check:Map[String,Option[Boolean]]) extends Exception("object is not valid: "+check.toString())

  def write(obj:Map[String,Any]): Try[Vertex] = obj.get("id") match {
    case Some(id)=>
      sg.nodeById(id.toString) match
      {


        case Some(nd)=>
          val props = nd.propsWithNew(obj)
          if(must.isValid(props))
          {
            sg.setParams(nd,props)
            //nd.addEdge(SG.TYPE,nd)
            Success(nd)
          }
          else Failure(new NotValidVertex(must.validate(props)))

        case _=>
          if(must.isValid(obj))
          {
            val nd = sg.addNode(obj)
            //nd.addEdge(SG.TYPE,nd)
            Success(nd)
          }
          else  Failure(new NotValidVertex(must.validate(obj)))
      }
    case None=>
      if(must.isValid(obj))
      {
        val nd = sg.addNode(obj)
        //nd.addEdge(SG.TYPE,nd)
        Success(nd)
      }
      else  Failure(new NotValidVertex(must.validate(obj)))
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

/* class that encapsulates restrictions*/
class Schema extends Properties with Links {

  def parse(v:Vertex, label:String): IterableView[Option[Property], Iterable[_]] =
  {
    v.getVertices(Direction.OUT, label).view.map(this.parseProp)
  }

  def write(v:Vertex) = this.writeLinks(this.writeProperties(v))


  def parseProp(v:Vertex): Option[Property] = v.getProperty[String](CONSTRAINT) match
  {


    case StringOf.constraint=> StringOf.parse(v)
    case IntegerOf.constraint=>IntegerOf.parse(v)
    case DateTimeOf.constraint=>DateTimeOf.parse(v)
    case HashOf.constraint=>HashOf.parse(v)
    case IntegerOf.constraint=>IntegerOf.parse(v)
    case DoubleOf.constraint=>DoubleOf.parse(v)


  }





}
