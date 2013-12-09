package models.graphs.views

import com.tinkerpop.blueprints.Vertex
import play.api.libs.json.{Json, JsValue}
import models.graphs.SG


case class NodeViewModel(iid:String, v:Vertex) extends Container(iid,v) {

  import SG._

  lazy val incoming = v.allInV.filter(_._2.isLink).map(kv=>EdgeViewModel(kv._2.id,kv._1,kv._2))
  lazy val outgoing = v.allOutV.filter(_._2.isLink).map(kv=>EdgeViewModel(kv._2.id,kv._1,kv._2))
  //lazy val both = v.allV.filter(_._2.isLink).map(kv=>EdgeViewModel(kv._2.id,kv._1,kv._2))



}

case class EdgeViewModel(iid:String, val label:String, v:Vertex) extends Container(iid,v) {


}

