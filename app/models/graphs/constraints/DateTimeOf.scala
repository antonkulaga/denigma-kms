package models.graphs.constraints

import com.github.nscala_time.time.Imports._
import com.tinkerpop.blueprints.{Element, Vertex}
import models.graphs.SemanticGraph
import models.graphs.SG._

object DateTimeOf extends PropertyParser[DateTimeOf]
{
  val constraint: String = "DATETIME"
  val BEFORE = "before"
  val AFTER = "after"


  def parse(v: Vertex): Option[DateTimeOf] = withName(v){

    (name,n)=>
      DateTimeOf(name,v.getProperty(BEFORE),v.getProperty(AFTER))

  }
}


case class DateTimeOf(propertyName:String, after:DateTime = null, before:DateTime = null) extends Property(propertyName)  with Validator[Long]   //with PropertyWriter
{
  def validate(value:Long) =  validate(value.toDateTime)

  def validate(dt:DateTime) = (after, before) match
  {
    case (null,null)=> true
    case (null, aft)=> dt>=aft
    case (bef,null) => dt<=bef
    case (bef,aft) =>   dt>=aft && dt<=bef
  }

  def write(v: Element): Element = {

    v.setProperty(NAME,name)
    if(before!=null) v.setProperty(DateTimeOf.BEFORE,before)
    if(after!=null)v.setProperty(DateTimeOf.AFTER,after)
    v.setProperty(CONSTRAINT,DateTimeOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:DateTime=>this.validate(v)
    case _=>false
  }


}
