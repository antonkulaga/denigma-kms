package models.graphs

import com.tinkerpop.blueprints.{Direction, Vertex}
import scala.collection.JavaConversions._
import play.Logger
import com.tinkerpop.blueprints._
import  scala.util.matching.Regex
import SG.SemanticNode

class constraint

//object Property
//{
//  def parse(element:Element,name:String):Option[Property] = if(element.getPropertyKeys.contains(name)) Some(new Property(name)) else None
//}
//
//class Property(name:String)
//{
//  def hasProperty(element:Element):Boolean = element.getPropertyKeys.contains(name)
//
//  def hasProperty(key:String) = name==key
//}
//
///*checks string property*/
//case class StringProperty(name:String,regExp:String) extends Property(name)
//{
//  val regex = regExp.r
//  def validate(key:String,value:String) = hasProperty(key) && validate(value)
//  def validate(value:String) = regex.findFirstIn(value) match {
//    case Some(str) => str == value
//    case None => false
//  }
//  def matches(str:String) = regex.findAllMatchIn(str)
//}
//
//object StringProperty
//{
//  val VALUE:String="value"
//  def parse(element:Element,name:String):Option[StringProperty] = if(element.getPropertyKeys.containsAll(List(name,VALUE)))
//    Some(new StringProperty(name,element.getProperty(VALUE)))
//  else None
//}
//
//case class IntProperty(name:String,min:Int, max:Int) extends Property(name)
//{
//  def validate(key:String,value:Int)  = hasProperty(key) && validate(value)
//
//  def validate(value:Int) = value>min && value < max
//}
//
//class NodeType
//{
//
//}
//
//
//class Link(name:String, dir:Direction)
//{
//  def hasLink(v:Vertex) = this.edges(v).iterator().hasNext
//  def edges(v:Vertex) = v.getEdges(dir,name)
//  def vertices(v:Vertex) = v.getVertices(dir,name)
//
//  def linkVertices(v:Vertex) = {
//    vertices(v).filter(p=>p.isLink())
//  }
//
//
//}


//class Must extends constraint
//case class MustHaveLink(link:String) extends constraint
//case class MustConnectTo(node:String) extends constraint
//case class MustConnectToNodeOf(nodeType:String) extends constraint
//case class MustHaveProperty(property:String) extends constraint
//case class MustHaveValue(property:String, value:String) extends constraint
//
//case class
// ShouldHaveLink(link:String) extends Should
//case class ShouldConnectTo(node:String) extends Should
//case class ShouldConnectToNodeOf(nodeType:String) extends Should
//case class ShouldHaveProperty(property:String) extends Should
//case class ShouldHaveValue(property:String, value:String) extends Should
//
//abstract class Constraint
//{
//    def check(v:Vertex):Boolean
//    def apply(v:Vertex):Vertex
//}
//
//abstract class Must(node:Vertex) extends Constraint
//
//class MustHave
//
//abstract class Should(node:Vertex) extends Constraint
//
//
////class Schema {
////  def parse(prop:String,node:Vertex):Constraint = prop match {
////    case "musthavelink"=>new MustHaveLink()
////  }
////}