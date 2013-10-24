package models.graphs.constraints
import com.tinkerpop.blueprints.Vertex
import models.graphs.{SG, GP, SemanticGraph}
import SG._



object StringOf extends PropertyParser[StringOf]
{
  val constraint: String = "STRING"
  val regex:String = "regex"


  def parse(v: Vertex): Option[StringOf] = withName(v){

    (name,n)=>
      StringOf(name,v.p[String](regex).getOrElse(""))

  }
}

/*checks string property*/
case class StringOf(propertyName:String,regExp:String="") extends Property(propertyName) with Validator[String] //with PropertyWriter
{

  val regex = regExp.r
  def validate(value:String): Boolean = if(regExp=="") true else regex.findFirstIn(value) match {
    case Some(str) => str == value
    case None => false
  }

  def matches(str:String) = regex.findAllMatchIn(str)


  def write(v: Vertex): Vertex = {

    v.setProperty(NAME,name)
    v.setProperty("regex",regExp)
    v.setProperty(CONSTRAINT,StringOf.constraint)
    v
  }
}