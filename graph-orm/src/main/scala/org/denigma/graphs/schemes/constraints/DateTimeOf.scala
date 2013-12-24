package org.denigma.graphs.schemes.constraints

import com.github.nscala_time.time.Imports._
import com.tinkerpop.blueprints.{Element, Vertex}
import org.denigma.graphs.SG
import SG._
import org.joda.time.DateTime


object DateTimeOf extends PropertyParser[DateTimeOf]
{
  val constraint: String = "DATETIME"
  val BEFORE = "before"
  val AFTER = "after"


  def parse(v: Vertex): Option[DateTimeOf] = withName(v){

    (name,n)=>
      DateTimeOf(name,before = v.dateTime(BEFORE).get,after = v.dateTime(AFTER).get,
        cap = v.p[String](CAPTION).getOrElse(""),prior=v.p[Int](PRIORITY).getOrElse(Int.MaxValue))

  }
}


case class DateTimeOf(propertyName:String,
                      after:DateTime = null, before:DateTime = null,
                      cap:String = "", prior:Int=Int.MaxValue) extends Property(propertyName,caption = cap, priority = prior)  with Validator[String]   //with PropertyWriter
{
  def validate(value:String): Boolean =  validate(DateTime.parse(value))

  def validate(dt:DateTime) = (after, before) match
  {
    case (null,null)=> true
    case (null, aft)=> dt>=aft
    case (bef,null) => dt<=bef
    case (bef,aft) =>   dt>=aft && dt<=bef
  }

  override def write(v: Element): Element = {
    super.write(v)
    if(before!=null){
      val isoB: String = before.toDateTimeISO().toString()
      v.setProperty(DateTimeOf.BEFORE,isoB)
    }



    if(after!=null){
      val isoA: String = after.toDateTimeISO().toString()
      v.setProperty(DateTimeOf.AFTER,isoA)
    }
    v.setProperty(CONSTRAINT,DateTimeOf.constraint)
    v
  }

  def checkValidity(value: Any): Boolean  = value match {
    case v:DateTime=>this.validate(v)
    case v:String=>this.validate(v)
    case _=>false
  }


}
