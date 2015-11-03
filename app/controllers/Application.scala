package controllers

import drivers.DataDriver
import play.api._
import play.api.db.DB
import play.api.libs.json.{JsError, Json}
import play.api.mvc._

class Application extends Controller {
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def create = ???
//    Action(parse.json) {
//    var data = Map[String, String]()
//    request => request.body.validate[(String, String)].map{
//      case (key, value) => data = data + (key -> value)
//    }.recoverTotal{
//      e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
//    }
//        Ok
//  }


  def update = ???
  def showAll() = Action {
    val dd = new DataDriver
    Ok(Json.toJson(dd.getAll))
  }
  def show(uid: Long) =  Action {
    val dd = new DataDriver
    Ok(Json.toJson(dd.getByUID(uid)))
  }
  def delete(uid: Long) = Action {
    val dd = new DataDriver
    if(dd.removeUser(uid)){
      Ok()
    } else {
      Ok("failure")
    }
  }
}
