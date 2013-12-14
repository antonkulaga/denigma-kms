package graphs.schemes.constraints

import com.github.t3hnar.bcrypt._
import com.tinkerpop.blueprints.{Element, Vertex}
import graphs.SG
import SG._


object HashOf extends PropertyParser[HashOf]
{
  val constraint: String = "HASH"


  def parse(v: Vertex): Option[HashOf] = withName(v){

    (name,n)=>
      HashOf(name,cap = v.p[String](CAPTION).getOrElse(""),
        prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue))

  }
}

case class HashOf(propertyName:String,cap:String="",prior:Int=Int.MaxValue) extends Property(propertyName,caption = cap,priority = prior) with Validator[String]
{


  def checkPassword(pass:String,hash:String) = pass.isBcrypted(hash)
  def makeHash(pass:String, salt:String): String = pass.bcrypt(salt)
  def makeHash(pass:String): String =  makeHash(pass,generateSalt)

  override def write(v: Element): Element = {
    super.write(v)
    v.setProperty(CONSTRAINT,HashOf.constraint)
    v
  }

  def validate(value: String): Boolean =  true

  def checkValidity(value: Any): Boolean  = value match {
    case v:String=>this.validate(v)
    case _=>false
  }
}