package models.graphs

import com.tinkerpop.blueprints.Element
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

class NodeViewModel(id:String, v:Element) extends Container(id,v) {


}

class EdgeViewModel(id:String, val label:String, v:Element) extends Container(id,v) {


}

class Container(val id:String,v:Element)
{
  lazy val properties: Map[String, String] = v.getPropertyKeys().map(k=>k->v.getProperty[Any](k).toString).toMap


  override def hashCode() = this.id.hashCode()



   override def equals(obj:Any):Boolean = obj match
   {
     case con:Container=>con.id==id
     case _=>false
   }

}

