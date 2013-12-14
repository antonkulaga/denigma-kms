package models

import org.denigma.macroses._

import org.denigma.macroses.Macros
import com.tinkerpop.blueprints.Direction

abstract class Model extends ModelLike{

}

case class Out(to:String,params:Map[String,Any]) extends MakeLink(to,params,Direction.OUT)
case class In(to:String,params:Map[String,Any]) extends MakeLink(to,params,Direction.IN)

class MakeLink(to:String,params:Map[String,Any],val dir:Direction)
trait LinkBy{

}