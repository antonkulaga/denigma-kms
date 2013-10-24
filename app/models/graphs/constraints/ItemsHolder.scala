package models.graphs.constraints

import scala.collection.immutable.Map


trait ItemsHolder[T,TP] {
  val main:TP
  var items = Map.empty[String,T]

  def add(key:String,value:T):TP = {items +=(key->value); main }
  def remove(key:String): TP = {items-=key; main}

}

//class PropertyHolder[T<:PropertyWriter,TP<:PropertyCollector](val main:TP) extends ItemsHolder[T,TP] {


class PropertyHolder[T<:Property,TP<:PropertyCollector](val main:TP) extends ItemsHolder[T,TP] {


  def add(value:T): TP = {this.add(value.name,value).add(value.name,value); main}
  def remove(value:T):TP = {this.remove(value.name).remove(value.name); main}

}

class PropertyCollector extends ItemsHolder[Property,PropertyCollector]{

  val main = this

}