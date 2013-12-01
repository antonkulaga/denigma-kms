package models.graphs.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import models.graphs.SG._
import models.graphs.constraints.TextOf
object TextOf extends PropertyParser[TextOf]
{
  val constraint: String = "TEXT"
  val len:String = "len"
  val defMax = 5000

  def parse(v: Vertex): Option[TextOf] = withName(v){

    (name,n)=>
      TextOf(name,v.p[Int]("len").getOrElse(100000))

  }
}

/*checks string property*/
case class TextOf(propertyName:String,len:Int = TextOf.defMax) extends Property(propertyName) with Validator[String] //with PropertyWriter
{


  def validate(value:String): Boolean = value.length<=len


  def write(v: Element): Element = {

    v.setProperty(NAME,name)
    v.setProperty(TextOf.len,len)
    v.setProperty(CONSTRAINT,TextOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:String=>this.validate(v)
    case _=>false
  }
}