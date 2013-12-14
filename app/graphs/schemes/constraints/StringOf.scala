package graphs.schemes.constraints

import com.tinkerpop.blueprints._
import graphs.SG
import SG._



object StringOf extends PropertyParser[StringOf]
{
  val constraint: String = "STRING"
  val regex:String = "regex"


  def parse(v: Vertex): Option[StringOf] = withName(v){

    (name,n)=>
      StringOf(name,v.p[String](regex).getOrElse(""),cap = v.p[String](CAPTION).getOrElse(""),prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue))

  }
}

/*checks string property*/
case class StringOf(propertyName:String,regExp:String="", cap:String="",prior:Int=Int.MaxValue) extends Property(propertyName,cap,priority = prior) with Validator[String] //with PropertyWriter
{

  val regex = regExp.r
  def validate(value:String): Boolean = if(regExp=="") true else regex.findFirstIn(value) match {
    case Some(str) => str == value
    case None => false
  }

  def matches(str:String) = regex.findAllMatchIn(str)


  override def write(v: Element): Element = {
    super.write(v)
    v.setProperty(StringOf.regex,regExp)
    v.setProperty(CONSTRAINT,StringOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:String=>this.validate(v)
    case _=>false
  }
}
