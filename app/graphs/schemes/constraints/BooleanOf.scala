package graphs.schemes.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import graphs.SG
import SG._
object BooleanOf extends PropertyParser[BooleanOf]
{
  val constraint: String = "BOOLEAN"
  val default = "DEFAULT"

  def parse(v: Vertex): Option[BooleanOf] = withName(v){

    (name,n)=>
      BooleanOf(name,v.bool(DEFAULT).getOrElse(false),
        cap = v.p[String](CAPTION).getOrElse(""),
        prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue))

  }
}

case class BooleanOf(propertyName:String,default:Boolean=false, cap:String="", prior:Int = Int.MaxValue) extends Property(propertyName,caption = cap,priority = prior) with Validator[Boolean]
{ // with PropertyWriter{
  def validate(value: Boolean): Boolean = true
  override def write(v: Element): Element = {
    super.write(v)
    v.setProperty(DEFAULT,default)
    v.setProperty(CONSTRAINT,BooleanOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:Boolean=>this.validate(v)
    case _=>false
  }

}