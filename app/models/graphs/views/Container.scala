package models.graphs.views

import com.tinkerpop.blueprints.Vertex
import collection.JavaConversions._

class Container(val id:String,v:Vertex)
{
  lazy val properties: Map[String, String] = v.getPropertyKeys().map(k=>k->v.getProperty[Any](k).toString).toMap


  override def hashCode() = this.id.hashCode()




  override def equals(obj:Any):Boolean = obj match
  {
    case con:Container=>con.id==id
    case _=>false
  }

}
