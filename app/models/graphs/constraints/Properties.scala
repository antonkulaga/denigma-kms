package models.graphs.constraints

import com.tinkerpop.blueprints.Vertex
import models.graphs.{SG, SemanticGraph}
import collection.JavaConversions._
import org.denigma.macroses.ModelLike

trait Properties extends PropertyCollector
{
  var strings = new PropertyHolder[StringOf,Properties](this)
  var texts = new PropertyHolder[TextOf,Properties](this)

  var hashes= new PropertyHolder[HashOf,Properties](this)
  var ints= new PropertyHolder[IntegerOf,Properties](this)
  var doubles= new PropertyHolder[DoubleOf,Properties](this)
  var dts= new PropertyHolder[DateTimeOf,Properties](this)
  var bools= new PropertyHolder[BooleanOf,Properties](this)

  def have(value:StringOf): Properties  = this.strings.add(value)
  def have(value:HashOf): Properties  = this.hashes.add(value)
  def have(value:IntegerOf): Properties   = this.ints.add(value)
  def have(value:DoubleOf): Properties  = this.doubles.add(value)
  def have(value:DateTimeOf): Properties   = this.dts.add(value)
  def have(value:BooleanOf): Properties  = this.bools.add(value)

  import models.graphs.SG._
  /*
  Writes property as connected constraint vertex
   */
  def writeProperties(v:Vertex)= {this.items.foreach
  {

    case (name,prop)=>
      v.outV(CONSTRAINT).find(_.has("name",name)) match
      {
        case None=>prop.write(v.addConnected(CONSTRAINT))
        case Some(vt)=>prop.write(vt)
      }

  }
  v
  }

  //def missing[T<: ModelLike](model:T): Set[String] = this.items.keySet.diff(model.toMap[T].keySet)

  def validate(mp:Map[String,Any]):Map[String,Option[Boolean]] =  items.foldLeft(Map.empty[String,Option[Boolean]]){
    case (acc: Map[String, Option[Boolean]],(key: String,prop: Property))=>
      acc + (key->mp.get(key).map(v=>
        prop.checkValidity(v)))
  }

}