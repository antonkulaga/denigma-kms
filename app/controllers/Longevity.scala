package controllers

import play.api.mvc.Action

import play.api.mvc._
import models._

import play.api.libs.json.{JsNull, Json}
import com.tinkerpop.blueprints.Vertex
import org.joda.time.DateTimeZone
import scala._
import models.graphs.SG._
import scala.collection.JavaConversions._
import models.graphs.views.EdgeViewModel
import models.graphs.views.NodeViewModel
import play.api.libs.json.JsObject
import scala.Some
import views.html._

case class Items(menu:List[String],flags:List[String])

/**
 * Created by antonkulaga on 12/11/13.
 */
object Longevity extends Controller{
  def index = Action {
    implicit request =>
      val flags = List("United Kingdom","Russia","Ukraine","Israel","Germany","France","Italy","United States","China","Turkey","Spain","Austria").sorted
      val items = List("About","Blog","ILA Manifesto","Take Action","Projects")
      val res = Items(items,flags)
      Ok(longevity.index(res,request)) //Ok(views.html.page("node","menu","0"))
  }

  def content = Action {
    implicit request =>
      Ok(longevity.content()) //Ok(views.html.page("node","menu","0"))
  }


}
