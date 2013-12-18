package graphs.schemes

import com.tinkerpop.blueprints.Vertex
import com.tinkerpop.blueprints.Direction
import scala.collection.JavaConversions._
import graphs.{Link, SG}
import SG._
import scala.collection.IterableView
import graphs.schemes.constraints._

/**
 * Created by antonkulaga on 12/13/13.
 */
/* class that encapsulates restrictions*/
class Schema extends Properties with Links {

  def parse(v:Vertex, label:String): IterableView[Option[Property], Iterable[_]] =
  {
    v.getVertices(Direction.OUT, label).view.map(this.parseProp)
  }

  def write(v:Vertex) = this.writeLinkOfs(this.writeProperties(v))

  /*
  validates both object and links
   */
  def isValid(id:String,props:Map[String,Any],links:Seq[Link] = List.empty[Link]) = propsAreValid(props) && linksValid(links,id)


  def parseProp(v:Vertex): Option[Property] = v.getProperty[String](CONSTRAINT) match
  {


    case StringOf.constraint=> StringOf.parse(v)
    case IntegerOf.constraint=>IntegerOf.parse(v)
    case DateTimeOf.constraint=>DateTimeOf.parse(v)
    case HashOf.constraint=>HashOf.parse(v)
    case IntegerOf.constraint=>IntegerOf.parse(v)
    case DoubleOf.constraint=>DoubleOf.parse(v)


  }





}
