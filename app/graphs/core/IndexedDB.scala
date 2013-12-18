package graphs.core


import com.tinkerpop.blueprints._
import graphs.SG
import java.util.UUID
import org.joda.time.DateTime
import org.neo4j.index.impl.lucene.LowerCaseKeywordAnalyzer
import scala.None
import scala.collection.JavaConversions._


/*
abstract class for graph databases with Indexes
 */
abstract class IndexedDB[T<: IndexableGraph] extends GraphDB[T] with DefIndexes[T]{

  protected lazy val lowerCaseAnalyzer  = classOf[LowerCaseKeywordAnalyzer].getName

  def index(name:String,fulltext:Boolean=true): Index[Vertex] = index(name,fulltext,g)

  def addIndex(name:String): Index[Vertex] =  addIndex(name,g)

  def addIndex(name:String,graph:T): Index[Vertex] =  graph.createIndex(name, classOf[Vertex] ,new Parameter("analyzer", lowerCaseAnalyzer))

  def addFullTextIndex(name:String): Index[Vertex] =  addFullTextIndex(name:String, g)

  def addFullTextIndex(name:String,graph:T): Index[Vertex] =  graph.createIndex(name, classOf[Vertex],new Parameter("analyzer", lowerCaseAnalyzer),new Parameter("type","fulltext"))


  def index(name:String,fulltext:Boolean,graph:T): Index[Vertex] = {
    var ind: Index[Vertex] = graph.getIndex(name,classOf[Vertex])
    if(ind==null)
    {
      ind = if(fulltext) addFullTextIndex(name,graph) else addIndex(name,graph)
      indexes = indexes + (ind.getIndexName -> ind)
    }
    ind
  }


  var indexes: Map[String, Index[Vertex]] = Map.empty[String,Index[Vertex]]

  def nodeById(id:String): Option[Vertex] = ids.get(GP.ID,id).headOption

  def nodeByIdOrName(id:String): Option[Vertex] = nodeById(id).orElse(nodeByName(id))

  def nodeByName(name: String): Option[Vertex] = names.get(GP.NAME, name).headOption


  def addNode(params:(String,Any)*):Vertex = this.addNode(UUID.randomUUID().toString,params:_*)

  def addNode(id:String,params: (String, Any)*): Vertex = {

    val v = g.addVertex(null)
    v.setProperty(ID, id)
    ids.put(ID, id, v)
    setParams(v, params: _*)
  }


  def addNode(params:Map[String,Any]): Vertex = params.get("id") match
  {
    case Some(id)=>addNode(id.toString,params)
    case None=>addNode(UUID.randomUUID().toString,params)

  }

  def addNode(id:String,params:Map[String,Any]): Vertex =
  {

    val v = g.addVertex(null)
    v.setProperty(ID, id)
    ids.put(ID, id, v)
    setParams(v, params,"id")

  }


  /* adds map of properties to the vertex*/
  def setParams(v:Vertex,params:Map[String,Any], skip:String*): Vertex =   {
    params.foreach{
      case ("id",value) if !skip.contains(SG.ID)=>
        v.setProperty(SG.ID,value)
        ids.put(SG.ID,value,v)
        v

      case (key,value:DateTime) if !skip.contains(SG.ID)=>
        val str: String = value.toDateTimeISO.toString()
        v.setProperty(key,str)
        v


      case (key,value) if !skip.contains(key)=>
        v.setProperty(key,value)
        indexes.get(key)  match
        {
          case Some(ind: Index[Vertex])=>  ind.put(key,value,v)
          case _ =>
        }
    }
    //g.commit()
    v
  }


  def setParams(v:Vertex,params:(String,Any)*): Vertex =   {
    params.foreach{
      //to make date work well
      case (key,value:DateTime)=>
        val iso: String = value.toDateTimeISO.toString
        v.setProperty(key,iso)
        indexes.get(key)  match
        {
          case Some(ind: Index[Vertex])=>  ind.put(key,iso,v)
          case _ =>
        }
      case (key,value)=>
        v.setProperty(key,value)
        indexes.get(key)  match
        {
          case Some(ind: Index[Vertex])=>  ind.put(key,value,v)
          case _ =>
        }
    }
    //g.commit()
    v
  }

}
