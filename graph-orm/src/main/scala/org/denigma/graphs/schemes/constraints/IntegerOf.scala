package org.denigma.graphs.schemes.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import org.denigma.graphs.SG
import SG._


object IntegerOf extends PropertyParser[IntegerOf]
{
  val MIN = "min"
  val MAX = "max"
  val constraint: String = "INTEGER"

  def parse(v: Vertex): Option[IntegerOf] = withName(v){

    (name,n)=>
      IntegerOf(name,v.int(MIN).getOrElse(Int.MinValue),v.int(MAX).getOrElse(Int.MaxValue),
        cap = v.p[String](CAPTION).getOrElse(""),prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue))

  }
}

case class IntegerOf(propertyName:String,min:Int=Int.MinValue, max:Int=Int.MaxValue, cap:String="",prior:Int=Int.MaxValue) extends Property(propertyName,cap,priority = prior)  with Validator[Int]  //with PropertyWriter
{
  def validate(value:Int) = value>min && value < max



  override def write(v: Element): Element = {
    super.write(v)
    v.setProperty(IntegerOf.MAX,max)
    v.setProperty(IntegerOf.MIN,min)
    v.setProperty(CONSTRAINT,IntegerOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:Integer=>this.validate(v)
    case _=>false
  }
}

