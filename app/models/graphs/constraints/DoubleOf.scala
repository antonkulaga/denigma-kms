package models.graphs.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import models.graphs.{SG, SemanticGraph, GP}
import SG._

object DoubleOf extends PropertyParser[DoubleOf]
{

  val MIN = "min"
  val MAX = "max"
  val constraint: String = "DOUBLE"

  def parse(v: Vertex): Option[DoubleOf] = withName(v){

    (name,n)=>
      DoubleOf(name,v.p[Double](MIN).getOrElse(Double.MinValue),v.p[Double](MAX).getOrElse(Double.MaxValue))

  }
}

case class DoubleOf(propertyName:String,min:Double= Double.MinValue, max:Double = Double.MaxValue) extends Property(propertyName)  with Validator[Double] // with PropertyWriter
{
  def validate(value:Double) = value>min && value < max

  def write(v: Element): Element = {

    v.setProperty(NAME,name)
    v.setProperty(DoubleOf.MAX,max)
    v.setProperty(DoubleOf.MIN,min)
    v.setProperty(CONSTRAINT,DoubleOf.constraint)
    v
  }
}