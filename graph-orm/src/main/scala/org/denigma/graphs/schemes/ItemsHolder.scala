package org.denigma.graphs.schemes

import scala.collection.immutable.Map
import org.denigma.graphs.schemes.constraints.{Property, Named}


trait ItemsHolder[T,TP] {
  val main:TP
  var items = Map.empty[String,T]

  def add(key:String,value:T):TP = {items +=(key->value); main }

  def remove(key:String): TP = {items-=key; main}
  def push(key:String,value:T):T = {items +=(key->value); value }
}

//class PropertyHolder[T<:PropertyWriter,TP<:PropertyCollector](val main:TP) extends ItemsHolder[T,TP] {
abstract class NamedHolder[T<:Named,TP] extends ItemsHolder[T,TP] {
  def add(value:T): TP = this.add(value.name,value)
  def remove(value:T):TP = this.remove(value.name)
  def push(value:T):T = this.push(value.name,value)
}


class PropertyHolder[T<:Property,TP<:PropertyCollector](val main:TP) extends NamedHolder[T,TP]
{
  /*
  TODO: make it look less complicated
   */
  override def  add(value:T): TP = {this.add(value.name,value).add(value); main}
  override def  push(value:T): T = {main.push(value); super.push(value.name,value)}
  override def remove(value:T):TP = {this.remove(value.name).remove(value); main}

}

class PropertyCollector extends NamedHolder[Property,PropertyCollector]{

  val main = this

}
