package models.graphs.views

import com.tinkerpop.blueprints.Vertex
import collection.JavaConversions._
import play.api.libs.json.{JsString, Json}



class Container(val id:String,v:Vertex)
{
  lazy val properties: Map[String, String] = v.getPropertyKeys.map(k=>k->v.getProperty[Any](k).toString).toMap
  lazy val props = properties.toList.map{
    case (key,value) if value.forall(_.isDigit) =>jsDouble(key,"number",value.toDouble)

    case (key,value) if value.forall(_.isDigit) =>jsDouble(key,"number",value.toDouble)
    case (key,value)  =>js(key,"text",value)
    case (key,value) if value.length>100 =>js(key,"string",value)
  }

  lazy val jsProps = Json.toJson(props)

  def js(key:String,tp:String,value:String) = Json.obj(  "name"->key,  "kind"->tp,"value"->value)
  def jsDouble(key:String,tp:String,value:Double) = Json.obj(  "name"->key,  "kind"->tp,"value"->value)
  def jsBool(key:String,tp:String,value:Boolean) = Json.obj(  "name"->key,  "kind"->tp,"value"->value)
  //def jsBool(key:String,tp:String,value:Boolean) = Json.obj(  "name"->key,  "kind"->tp,"value"->value)



  override def hashCode() = this.id.hashCode()




  override def equals(obj:Any):Boolean = obj match
  {
    case con:Container=>con.id==id
    case _=>false
  }

}
