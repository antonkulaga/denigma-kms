package models.graphs

import com.tinkerpop.blueprints.{Direction, Vertex}
import scala.collection.immutable.Map
import scala.collection.immutable._


abstract class Constraint
{
    def check(v:Vertex):Boolean
    def apply(v:Vertex):Vertex
}

trait Properties
{
  var strings: Map[String, StringOf] = Map.empty[String,StringOf]
  var hashes: Map[String, HashOf] = Map.empty[String,HashOf]
  var ints: Map[String, IntegerOf] = Map.empty[String,IntegerOf]
  var doubles: Map[String, DoubleOf] = Map.empty[String,DoubleOf]
  var dts: Map[String,DateTimeOf] = Map.empty[String,DateTimeOf]
  var bools:Map[String,BooleanOf] = Map.empty[String,BooleanOf]

  def have(string:StringOf): Properties = {strings +=(string.name->string); this }
  def have(hash:HashOf): Properties = {hashes +=(hash.name->hash); this }
  def have(int:IntegerOf): Properties  = {ints +=(int.name->int); this }
  def have(double:DoubleOf): Properties  = {doubles +=(double.name->double); this }
  def have(dt:DateTimeOf): Properties  = {dts+=(dt.name->dt); this }
  def have(bool:BooleanOf): Properties  = {bools+=(bool.name->bool); this }


}

trait Links
{
  var links = Map.empty[String,Link]
  def have(link:Link): Links = {links+=(link.name->link); this }

}

class LinkType(name:String) extends NodeType(name)
{
  must have StringOf(GP.LINK)

}

class NodeType(val name:String)
{
  val should = new Schema()
  val must = new Schema()

}

class Schema extends Properties with Links

