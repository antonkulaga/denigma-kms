package controllers


import play.api.mvc._

import scala._
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

//  def names = Asyn.async {
//
//  }


}
