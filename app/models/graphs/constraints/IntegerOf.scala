package models.graphs.constraints

import com.tinkerpop.blueprints.Vertex
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

case class IntegerOf(propertyName:String,min:Int, max:Int) extends Property(propertyName)  with Validator[Int]  //with PropertyWriter
{
  def validate(value:Int) = value>min && value < max

  //def makeHashSalt(pass:String):(String,String) =  bcrypt.generateSalt match {case salt=>(makeHash(pass,bcrypt.generateSalt),salt) }
  def write(v: Vertex): Vertex = {

    v.setProperty(NAME,name)
    v.setProperty(CONSTRAINT,constraint)
    v
  }

  val constraint: String = "HASH"
}

