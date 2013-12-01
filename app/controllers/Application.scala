package controllers

import play.api.mvc._
import models._

import play.api.libs.json.{JsNull, JsObject, Json}
import com.tinkerpop.blueprints.Vertex
import models.graphs.SG
import org.joda.time.DateTimeZone
import scala.collection.immutable.IndexedSeq
import models.graphs.views.NodeViewModel
import models.graphs.views.EdgeViewModel
import scala.collection._
import scala._
import models.graphs.views.EdgeViewModel
import models.graphs.views.NodeViewModel
import models.graphs.SG._
import scala.collection.JavaConversions._
import models.graphs.views.EdgeViewModel
import models.graphs.views.NodeViewModel
import play.api.libs.json.JsObject
import scala.Some

object Application extends Controller with GenGraph{

  DateTimeZone.setDefault(DateTimeZone.UTC)

  def index = Action {
    implicit request =>
      Ok(views.html.main(List("Graph","Grid","LifeSpan"))) //Ok(views.html.page("node","menu","0"))
  }


  def node(id:String) = Action {
    implicit request =>
      Ok(views.html.graphs.vertex(id)) //Ok(views.html.page("node","menu","0"))
  }

  def vertices(id:String) = Action {
    implicit request =>
      vo(id) match {
        case None=>
          Ok(Json.obj("vertices" ->  JsNull)).as("application/json")
        case Some(r)=>
          Ok(Json.obj("vertices" -> Json.toJson(List(this.node2js(r))))).as("application/json") //Ok(views.html.page("node","menu","0"))
      }
  }



  def node2js(nv:NodeViewModel): JsObject = Json.obj("id" -> nv.id,
    "name" -> ("node_id_" + nv.id),
    "properties" -> Json.toJson(nv.properties)
  )


  def node2js(v:Vertex): JsObject  = this.node2js(new NodeViewModel(v.id,v))



  def edge2js(e:EdgeViewModel) = Json.obj("id" -> e.id,
    "name" -> ("node_id_" + e.id),
    "properties" -> Json.toJson(e.properties),
    "incoming" -> Json.toJson(e.v.inV().map(node2js)),
    "outgoing" -> Json.toJson(e.v.outV().map(node2js))
  )

  //def getV(id:String) = sg.nodeByIdOrName(id).orElse(TestGraph.rootOption())
  def vo(id:String) = sg.nodeByIdOrName(id).orElse(TestGraph.rootOption())


  /*
  gets incoming edges as json
   */
  def incoming(to:String) = Action{
    implicit request =>
      vo(to) match
      {
        case None =>Ok(Json.obj("edges" ->  JsNull)).as("application/json")
        case Some(vert) =>
          val edges = vert.allInV.map{case (label:String,v:Vertex)=>new EdgeViewModel(v.id, label, v)}
          Ok(Json.obj("edges" -> Json.toJson(edges.map(edge2js)))).as("application/json") //Ok(views.html.page("node","menu","0"))
    }
  }

  /*
  gets outgoing edges as json
   */
  def outgoing(from:String) = Action{
    implicit request =>
      vo(from) match
      {
        case None =>Ok(Json.obj("edges" ->  JsNull)).as("application/json")
        case Some(vert) =>
          val edges = vert.allOutV.map{case (label:String,v:Vertex)=>new EdgeViewModel(v.id, label, v)}
          Ok(Json.obj("edges" -> Json.toJson(edges.map(edge2js)))).as("application/json") //Ok(views.html.page("node","menu","0"))
      }
  }




}