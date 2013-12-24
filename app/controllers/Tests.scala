package controllers

import play.api.mvc.Controller
import play.api.mvc._


/**
 * Created by antonkulaga on 12/21/13.
 */
object Tests  extends Controller with GenGraph{
  def editor = Action {
    implicit request =>
      Ok(views.html.test.editor()) //Ok(views.html.page("node","menu","0"))
  }

  def sigma = Action {
    implicit request =>
      Ok(views.html.test.sigma()) //Ok(views.html.page("node","menu","0"))
  }
}
