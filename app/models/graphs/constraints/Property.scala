package models.graphs.constraints

import com.tinkerpop.blueprints.{Direction, Vertex, Element}
import models.graphs.{SG, SemanticGraph}
import SG._


abstract class Property(val name:String)
{
  def hasProperty(element:Element):Boolean = element.getPropertyKeys.contains(name)

  def hasProperty(key:String) = name==key

  //def makeHashSalt(pass:String):(String,String) =  bcrypt.generateSalt match {case salt=>(makeHash(pass,bcrypt.generateSalt),salt) }
  def write(v: Element): Element
}

trait Validator[T] extends Property{

  def validate(key:String,value:T): Boolean  =  hasProperty(key) && validate(value)
  def validate(value:T): Boolean
  def validate(el:Element):Boolean = el.getProperty[T](name) match {
    case null=>false
    case value:T => this.validate(value)
  }

}