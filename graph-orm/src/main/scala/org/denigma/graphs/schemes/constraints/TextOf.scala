package org.denigma.graphs.schemes.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import org.denigma.graphs.SG
import SG._
object TextOf extends PropertyParser[TextOf]
{
  val constraint: String = "TEXT"
  val len:String = "len"
  val defMax = 5000

  def parse(v: Vertex): Option[TextOf] = withName(v){

    (name,n)=>
      TextOf(name,v.p[Int]("len").getOrElse(100000),cap = v.p[String](CAPTION).getOrElse(""),prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue))

  }
}

/*checks string property*/
case class TextOf(propertyName:String,len:Int = TextOf.defMax, cap:String="",prior:Int=Int.MaxValue) extends Property(propertyName,cap,priority=prior) with Validator[String] //with PropertyWriter
{


  def validate(value:String): Boolean = value.length<=len


  override def  write(v: Element): Element = {
    super.write(v)
    v.setProperty(TextOf.len,len)
    v.setProperty(CONSTRAINT,TextOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:String=>this.validate(v)
    case _=>false
  }
}