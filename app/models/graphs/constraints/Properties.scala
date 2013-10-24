package models.graphs.constraints

import com.tinkerpop.blueprints.Vertex
import models.graphs.SemanticGraph

trait Properties extends PropertyCollector
{
  var strings = new PropertyHolder[StringOf,Properties](this)
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
//
//  def write(v:Vertex)(implicit  sg:SemanticGraph) = {
//
//    this.items.foreach{ _.write(v)
//
//
//    }
//
//  }
}