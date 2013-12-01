package models.graphs.constraints

import com.github.t3hnar.bcrypt._
import com.tinkerpop.blueprints.{Element, Vertex}
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

  def write(v: Element): Element = {

    v.setProperty(NAME,name)
    v.setProperty(CONSTRAINT,HashOf.constraint)
    v
  }
}