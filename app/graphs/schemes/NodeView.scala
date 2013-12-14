package graphs.schemes

import graphs.schemes.constraints.Property
import com.tinkerpop.blueprints.Vertex
import graphs.SG._
import scala.Some
import play.Logger


//class LinkNodeView(label:String*)
//
//class NodeView(name:String){
//  var types = Map.empty[String,NodeType]
//
//  def property[T<:Property](prop:T,nd:NodeType) = property(prop,nd,prop.name,0)
//
//  def property[T<:Property](prop:T,nd:NodeType,caption:String,position:Int) = {
//    val p = PropertyView(prop,nd,caption,position)
//  }
//}
//
//object NodeView {
//
//  def parse(v:Vertex):Option[NodeType] = {
//
//    val name = v.getProperty(NAME)
//    if(name == null) {
//      Logger.error(s"there is no view with $name")
//      return None
//    }
//    val res = new NodeType(name)
//    res.must.parse(v,MUST)
//    res.should.parse(v,SHOULD)
//    Some(res)
//  }
//
//}
//
//case class PropertyView[T<:Property](prop:T,var nodeType:NodeType,caption:String="",position:Int = 0){
//  def from(nd:NodeType) = {
//    nodeType = nd
//    this
//  }
//}