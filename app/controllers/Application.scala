package controllers

import play.api._
import play.api.mvc._
import models._

import play.api.libs.json.{JsArray, JsObject, JsValue, Json}
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import scala.collection.JavaConversions._
import models.graphs.{SG, SemanticGraph}
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

  def vertex = Action {
    implicit request =>

      def props = Json.toJson((1 until 5).map(r => ("property_"+r, "value+"+r)).toMap)
      val nodes: IndexedSeq[JsObject] = (1 until 10).map(v =>
        Json.obj("id" -> v.toString,
          "name" -> ("node_name_" + v.toString),
          "description" -> ("DESC about " + v.toString),
          "properties" -> props)
      )

      val res = Json.toJson(nodes)


      //val v = TestGraph.init()
      Ok(Json.obj("vertices"->res)).as("application/json") //Ok(views.html.page("node","menu","0"))
  }


}