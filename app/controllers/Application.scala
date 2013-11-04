package controllers

import play.api._
import play.api.mvc._
import models._

import play.api.libs.json.Json
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import scala.collection.JavaConversions._
import models.graphs.SemanticGraph
import org.joda.time.DateTimeZone


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


}