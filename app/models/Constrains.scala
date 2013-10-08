package models

import com.tinkerpop.blueprints.Vertex


class constraint
//class Must extends constraint
//case class MustHaveLink(link:String) extends constraint
//case class MustConnectTo(node:String) extends constraint
//case class MustConnectToNodeOf(nodeType:String) extends constraint
//case class MustHaveProperty(property:String) extends constraint
//case class MustHaveValue(property:String, value:String) extends constraint
//
//case class ShouldHaveLink(link:String) extends Should
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