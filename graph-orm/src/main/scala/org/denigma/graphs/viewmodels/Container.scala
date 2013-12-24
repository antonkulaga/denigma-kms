package org.denigma.graphs.viewmodels

import com.tinkerpop.blueprints.Vertex
import collection.JavaConversions._
import play.api.libs.json.{JsString, Json}



class Container(val id:String,v:Vertex)
{
  lazy val properties: Map[String, Any] = v.getPropertyKeys.map(k=>k->v.getProperty[Any](k)).toMap
  lazy val props = properties.toList.map{
    case (key,value:Int) =>jsInt(key,"number",value)
    case (key,value:Double) =>jsDouble(key,"number",value.toDouble)
    case (key,value:String) if value.length>100 =>js(key,"string",value)
    case (key,value:String)  =>js(key,"text",value)

  }

  lazy val jsProps = Json.toJson(props)

  def js(key:String,tp:String,value:String) = Json.obj(  "name"->key,  "kind"->tp,"value"->value)
  def jsInt(key:String,tp:String,value:Int) = Json.obj(  "name"->key,  "kind"->tp,"value"->value)

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
