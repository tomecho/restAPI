package controllers

import drivers.DataDriver
import play.api._
import play.api.db.DB
import play.api.libs.json.Json
import play.api.mvc._

class Application extends Controller {
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def create = ???
    //request.body.validate[(Long,String,String,Long,Long)].map { //uid, first, last, dob, dod
     // case name
    //}
  def update = ???
  def showAll() = Action {
    val dd = new DataDriver
    Ok(Json.toJson(dd.getAll))
  }
  def show(uid: Long) =  Action {
    val dd = new DataDriver
    Ok(Json.toJson(dd.getByUID(uid)))
  }
  def delete = ???
}
