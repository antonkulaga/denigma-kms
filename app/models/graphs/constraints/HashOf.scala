package models.graphs.constraints

import com.github.t3hnar.bcrypt._
import com.tinkerpop.blueprints.Vertex
import models.graphs.{SG}
import SG._


object HashOf extends PropertyParser[HashOf]
{
  val constraint: String = "HASH"


  def parse(v: Vertex): Option[HashOf] = withName(v){

    (name,n)=>
      HashOf(name)

  }
}

case class HashOf(propertyName:String) extends Property(propertyName) //with PropertyWriter
{

  def checkPassword(pass:String,hash:String) = pass.isBcrypted(hash)
  def makeHash(pass:String, salt:String): String = pass.bcrypt(salt)
  def makeHash(pass:String): String =  makeHash(pass,generateSalt)

  //def makeHashSalt(pass:String):(String,String) =  bcrypt.generateSalt match {case salt=>(makeHash(pass,bcrypt.generateSalt),salt) }
  def write(v: Vertex): Vertex = {

    v.setProperty(NAME,name)
    v.setProperty(CONSTRAINT,constraint)
    v
  }

  val constraint: String = "HASH"
}