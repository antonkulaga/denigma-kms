package models.graphs.constraints

import models.graphs.{SG, SemanticGraph, GP}
import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import play.Logger
import scala.collection.JavaConversions._
import SG._


class LinkType(name:String) extends NodeType(name)
{
  must have StringOf(GP.LINK)

}

class NodeType(val name:String)
{
  val should = new Schema()
  val must = new Schema()

//  def write(v:Vertex) = {
//
//    this.must.items.foreach(p=>
//    p.write(v))
//  }

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

class Schema extends Properties with Links {
  def parse(v:Vertex, label:String) = {
    v.getVertices(Direction.OUT, label).view.foreach(this.parseProp)
  }


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
