package controllers

import play.api._
import play.api.mvc._
import models._

import play.api.libs.json.Json
import com.tinkerpop.blueprints.Graph
import com.tinkerpop.blueprints.impls.orient.OrientGraph
import scala.collection.JavaConversions._
import models.SemanticGraph



object Application extends Controller {

  def index = Action {
    implicit request =>
      Ok(views.html.main(List("Graph","Grid","LifeSpan"))) //Ok(views.html.page("node","menu","0"))
  }


  def node(id:String) = Action {
    implicit request =>
      Ok(views.html.graphs.node(id)) //Ok(views.html.page("node","menu","0"))
  }



}