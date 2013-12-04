package models.graphs

import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import scala.collection.JavaConversions._
import SG._

/* need to constract the Link */
abstract class LinkBuilder(v:Vertex,d:Direction,label:String) {
    lazy val link: Option[Vertex] = v.getVertices(Direction.OUT, label).find(_.isLink(label))


}

/* abstract basic class that is needed to create syuntax sugar for links*/
abstract class LinkCreator(v:Vertex,d:Direction,label:String, props:(String,String)*) extends LinkBuilder(v,d,label) {

  def l: Vertex = this.link match {
    case Some(ll)=>ll
    case None =>  if(d==Direction.OUT) v.addConnected(label,props:_*) else {
      val ll = sg.addNode(props: _*)
      ll.addEdge(label,v)
      ll.toLink(label)
      ll
    }

  }

}

/* syntax sugar for out linkgs creation*/
class LinkOutCreator(v:Vertex,label:String, props:(String,String)*) extends LinkCreator(v,Direction.OUT,label,props:_*){

  def ~>(node:Vertex): Vertex = {
    l.addEdge(label,node)
    node
  }

  def ~>(params:(String,String)*):Vertex = this ~>  sg.addNode(params:_*)


  def ~>(id:String,params: (String, String)*): Vertex = this ~>  sg.addNode(id, params:_*)
}

class LinkInCreator(v:Vertex,label:String, props:(String,String)*) extends LinkCreator(v,Direction.IN,label,props:_*){

  def ~(node:Vertex): Vertex =  {
    node.addEdge(label,v)
    node
  }


  def ~(params:(String,String)*):Vertex = this ~ sg.addNode(params:_*)

  def ~(id:String,params: (String, String)*): Vertex =  this ~ sg.addNode(id,params:_*)
}
