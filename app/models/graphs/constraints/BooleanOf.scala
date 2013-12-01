package models.graphs.constraints



import com.tinkerpop.blueprints.{Element, Vertex}
import models.graphs.{SG, SemanticGraph}
import SG._
object BooleanOf extends PropertyParser[BooleanOf]
{
  val constraint: String = "BOOLEAN"
  val default = "DEFAULT"

  def parse(v: Vertex): Option[BooleanOf] = withName(v){

    (name,n)=>
      BooleanOf(name,v.bool(DEFAULT).getOrElse(false))

  }
}

case class BooleanOf(propertyName:String,default:Boolean=false) extends Property(propertyName) with Validator[Boolean]
{ // with PropertyWriter{
  def validate(value: Boolean): Boolean = true
  def write(v: Element): Element = {
    v.setProperty(NAME,name)
    v.setProperty(DEFAULT,default)
    v.setProperty(CONSTRAINT,BooleanOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:Boolean=>this.validate(v)
    case _=>false
  }

}