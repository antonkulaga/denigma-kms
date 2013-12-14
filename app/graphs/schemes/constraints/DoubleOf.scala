package graphs.schemes.constraints

import com.tinkerpop.blueprints.{Element, Vertex}
import graphs.SG
import SG._

object DoubleOf extends PropertyParser[DoubleOf]
{

  val MIN = "min"
  val MAX = "max"
  val constraint: String = "DOUBLE"

  def parse(v: Vertex): Option[DoubleOf] = withName(v){

    (name,n)=>
      DoubleOf(name,v.p[Double](MIN).getOrElse(Double.MinValue),
        v.p[Double](MAX).getOrElse(Double.MaxValue),
        cap = v.p[String](CAPTION).getOrElse(""),
        prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue)
      )

  }
}

case class DoubleOf(propertyName:String,
                    min:Double= Double.MinValue, max:Double = Double.MaxValue,
                    cap:String="",prior:Int=Int.MaxValue) extends Property(propertyName,caption = cap, priority = prior)  with Validator[Double] // with PropertyWriter
{
  def validate(value:Double) = value>min && value < max

  override def write(v: Element): Element = {
    super.write(v)
    v.setProperty(DoubleOf.MAX,max)
    v.setProperty(DoubleOf.MIN,min)
    v.setProperty(CONSTRAINT,DoubleOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:Double=>this.validate(v)
    case _=>false
  }
}