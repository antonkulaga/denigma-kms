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
//      val g = SemanticGraph.g
//      g.getVertices("name","menu").iterator().toList.foreach(v=>g.removeVertex(v))
//      //g.addVertex(null,"name","menu").save()
////      g.addVertex(null,"name","some").save()
////      g.addVertex(null,"name","menu").save()
//
//
//      //val m:String = v.getProperty("name")
//      val iter = g.getVertices("name","menu").iterator()
//
//      val context: List[String] = iter.map(v=>v.getProperty[String]("name")).toList
//
      val context: List[String] = List("test","test")

      //Set(m)
      Ok(views.html.test(context)) //Ok(views.html.page("node","menu","0"))
  }

//  def menu = Action {
//    implicit request => Ok(views.html.menu(Menu.get("main")))
//  }
//
//
//  def node(v:String) = page("menu",v)
//
//
//  def page(mv:String,v:String) = Action {
//    implicit request =>  Ok(views.html.page("node",mv,v))
//
//  }
//
//  def giveme(arg:String) = Action {
//    Ok(Json.toJson("hello," + arg ))
//  }
//
//  def some = Action{
//    val localGraph:Graph  = new OrientGraph("local:/tmp/tinkerpop")
//
//    Ok(Json.toJson("hello,"))
//  }


}