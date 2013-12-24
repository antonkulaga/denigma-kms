package org.denigma.graphs.schemes.constraints
import org.denigma.graphs.SG._
import com.tinkerpop.blueprints.Element
import org.denigma.graphs.SG

trait Named
{
  val name:String
}

/*
Abstract property constraint class
 */
abstract class Property(val name:String, val caption:String, val priority:Int) extends Named
{

  def hasProperty(element:Element):Boolean = element.getPropertyKeys.contains(name)

  def hasProperty(key:String) = name==key

  def title(element:Element) = element.getProperty[String](SG.TITLE) match {case null=>name; case ""=>name case t=>t}

  def title = if(caption=="") name else caption

  //def makeHashSalt(pass:String):(String,String) =  bcrypt.generateSalt match {case salt=>(makeHash(pass,bcrypt.generateSalt),salt) }
  def write(v: Element): Element = {

    v.setProperty(NAME,name)
    v.setProperty(PRIORITY,priority)
    if(caption!="")v.setProperty(CAPTION,caption)
    v
  }

  def checkValidity(value:Any):Boolean
}

trait Validator[T] extends Property{
  def checkValidity(value:Any):Boolean

  def validate(key:String,value:T): Boolean  =  hasProperty(key) && validate(value)
  def validate(value:T): Boolean
  def validate(el:Element):Boolean = el.getProperty[T](name) match {
    case null=>false
    case value:T => this.validate(value)
  }

}