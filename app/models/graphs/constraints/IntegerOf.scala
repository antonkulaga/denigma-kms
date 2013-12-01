package models.graphs.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import models.graphs.SG._


object IntegerOf extends PropertyParser[IntegerOf]
{
  val MIN = "min"
  val MAX = "max"
  val constraint: String = "INTEGER"

  def parse(v: Vertex): Option[IntegerOf] = withName(v){

    (name,n)=>
      IntegerOf(name,v.int(MIN).getOrElse(Int.MinValue),v.int(MAX).getOrElse(Int.MaxValue))

  }
}

case class IntegerOf(propertyName:String,min:Int=Int.MinValue, max:Int=Int.MaxValue) extends Property(propertyName)  with Validator[Int]  //with PropertyWriter
{
  def validate(value:Int) = value>min && value < max



  def write(v: Element): Element = {

    v.setProperty(NAME,name)
    v.setProperty(IntegerOf.MAX,max)
    v.setProperty(IntegerOf.MIN,min)
    v.setProperty(CONSTRAINT,IntegerOf.constraint)
    v
  }
}

