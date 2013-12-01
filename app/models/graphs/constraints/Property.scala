package models.graphs.constraints

import com.tinkerpop.blueprints.{Direction, Vertex, Element}
import models.graphs.{SG, SemanticGraph}
import SG._
import scala.reflect.runtime.universe._
import javassist.bytecode.stackmap.TypeTag


abstract class Property(val name:String)
{
  def hasProperty(element:Element):Boolean = element.getPropertyKeys.contains(name)

  def hasProperty(key:String) = name==key

  //def makeHashSalt(pass:String):(String,String) =  bcrypt.generateSalt match {case salt=>(makeHash(pass,bcrypt.generateSalt),salt) }
  def write(v: Element): Element

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