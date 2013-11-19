package controllers

import play.api._
import play.api.mvc._
import models._

import play.api.libs.json.{JsArray, JsObject, JsValue, Json}
import com.tinkerpop.blueprints.{Vertex, Graph}
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import scala.collection.JavaConversions._
import models.graphs.{NodeViewModel, SG, SemanticGraph}
import org.joda.time.DateTimeZone
import scalax.collection.mutable.Graph
import scala.collection.immutable.IndexedSeq


object Application extends Controller {

  DateTimeZone.setDefault(DateTimeZone.UTC)

  def index = Action {
    implicit request =>
      Ok(views.html.main(List("Graph","Grid","LifeSpan"))) //Ok(views.html.page("node","menu","0"))
  }


  def node(id:String) = Action {
    implicit request =>
      Ok(views.html.graphs.vertex(id)) //Ok(views.html.page("node","menu","0"))
  }

  def test = Action {
    implicit request =>
      Ok(views.html.test()) //Ok(views.html.page("node","menu","0"))
  }

  def hallo = Action {
    implicit request =>
      Ok(views.html.hallo()) //Ok(views.html.page("node","menu","0"))
  }

  def vertex = Action {
    implicit request =>

      import SG._
      val tg = TestGraph
      val r: Vertex = tg.root()//tg.init()
      val vm = new NodeViewModel(r.id, r)
      val nodes: IndexedSeq[JsObject] = (0 until 5).map(v =>
        Json.obj("id" -> v.toString,
          "name" -> ("node_name_" + v.toString),
          "description" -> ("DESC about " + v.toString),
          "properties" -> Json.toJson(vm.properties)
        )
      )

      Ok(Json.obj("vertices" -> Json.toJson(nodes))).as("application/json") //Ok(views.html.page("node","menu","0"))

      //Ok(Json.obj("vertices"->genNodes())).as("application/json") //Ok(views.html.page("node","menu","0"))
  }




  def genNodes(ns:Int=5, prs:Int=5): JsValue = {
    def props = Json.toJson((0 until prs).map(r => ("property_" + r, "value+" + r)).toMap)

    val nodes: IndexedSeq[JsObject] = (0 until ns).map(v =>
      Json.obj("id" -> v.toString,
        "name" -> ("node_name_" + v.toString),
        "description" -> ("DESC about " + v.toString),
        "properties" -> props
      )
    )
      Json.toJson(nodes)

  }

  def edge = Action {
    implicit request =>

      //val v = TestGraph.init()
      Ok(Json.obj("edges" -> genEdges)).as("application/json") //Ok(views.html.page("node","menu","0"))
  }
  def genEdges: JsValue = {
    def props = Json.toJson((1 until 2).map(r => ("property_" + r, "value+" + r)).toMap)
    val edges: IndexedSeq[JsObject] = (1 until 10).map(e =>
      Json.obj("id" -> e.toString,
        "name" -> ("edge_name_" + e.toString),
        "description" -> ("DESC about " + e.toString),
        "properties" -> props
        , "incoming" -> genNodes(2,2)
        , "outgoing" -> genNodes(3, 3)

      )
    )

    Json.toJson(edges)
  }

}