package models.graphs

import scala.collection.JavaConversions._
import com.tinkerpop.blueprints._
import scala.collection.immutable._
import com.github.t3hnar.bcrypt._
import com.github.nscala_time.time.Imports._


object Property
{
  def parse(element:Element,name:String):Option[Property] = if(element.getPropertyKeys.contains(name)) Some(new Property(name)) else None
}

class Property(val name:String)
{
  def hasProperty(element:Element):Boolean = element.getPropertyKeys.contains(name)

  def hasProperty(key:String) = name==key
}

trait Validator[T] extends Property{

  def validate(key:String,value:T): Boolean  =  hasProperty(key) && validate(value)
  def validate(value:T): Boolean
  def validate(el:Element):Boolean = el.getProperty[T](name) match {
    case null=>false
    case value:T => this.validate(value)
  }

}

case class BooleanOf(propertyName:String,default:Boolean=false) extends Property(propertyName) with Validator[Boolean]{
  def validate(value: Boolean): Boolean = true
}

/*checks string property*/
case class StringOf(propertyName:String,regExp:String="") extends Property(propertyName) with Validator[String]
{
  val regex = regExp.r
  def validate(value:String): Boolean = if(regExp=="") true else regex.findFirstIn(value) match {
    case Some(str) => str == value
    case None => false
  }

  def matches(str:String) = regex.findAllMatchIn(str)

}

case class HashOf(propertyName:String) extends Property(propertyName){

  def checkPassword(pass:String,hash:String) = pass.isBcrypted(hash)
  def makeHash(pass:String, salt:String): String = pass.bcrypt(salt)
  def makeHash(pass:String): String =  makeHash(pass,generateSalt)

  //def makeHashSalt(pass:String):(String,String) =  bcrypt.generateSalt match {case salt=>(makeHash(pass,bcrypt.generateSalt),salt) }

}

object StringProperty
{
  val VALUE:String="value"
  def parse(element:Element,name:String):Option[StringOf] = if(element.getPropertyKeys.containsAll(List(name,VALUE)))
    Some(StringOf(name,element.getProperty(VALUE)))
  else None
}


case class IntegerOf(propertyName:String,min:Int, max:Int) extends Property(propertyName)  with Validator[Int]
{
  def validate(value:Int) = value>min && value < max
}

case class DoubleOf(propertyName:String,min:Int, max:Int) extends Property(propertyName)  with Validator[Int]
{
  def validate(value:Int) = value>min && value < max
}

//case class DateOf(propertyName:String, date: LocalDate) extends Property(propertyName)  with Validator[LocalDate]
//{
//  def validate(value:Int) = date.toLocalDateTime()
//}

case class DateTimeOf(propertyName:String, after:DateTime = null, before:DateTime = null) extends Property(propertyName)  with Validator[Long]
{
  def validate(value:Long) =  validate(value.toDateTime)

  def validate(dt:DateTime) = (after, before) match
  {
    case (null,null)=> true
    case (null, aft)=> dt>=aft
    case (bef,null) => dt<=bef
    case (bef,aft) =>   dt>=aft && dt<=bef
  }


}
