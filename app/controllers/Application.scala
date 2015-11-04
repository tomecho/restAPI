package controllers

import drivers.DataDriver
import drivers.User
import play.api._
import play.api.db.DB
import play.api.libs.json._
import play.api.mvc._
import play.api.libs.functional.syntax._

class Application extends Controller {
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  implicit val read = (
    (__ \ 'firstName).read[String] and
      (__ \ 'lastName).read[String] and
      (__ \ 'dob).read[String] and
      (__ \ 'dod).read[String]
    ) tupled
  //todo: format properly to take in json data
  def create = Action(parse.json) { request =>
    val dd = new DataDriver
    request.body.validate[(String, String,String, String)].map{
      case (firstName,lastName,dob,dod) =>
        Ok(
          dd.addUser(new User(
            firstName,lastName,dob.toLong,dod.toLong)).toString)
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
    }
  }

  //todo: do the update
  def update(uid: String) = {
    show(uid)
  }
  //TODO: reformat response to expected response from project specifications
  def showAll() = Action {
    val dd = new DataDriver
    /*val json: JsValue = dd.getAll.foreach(JsObject(
      "url" -> JsString(_)
    ))*/
    Ok(Json.toJson(dd.getAll))
  }

  /** * Print all users
    *
    * @param uid
    * @return Action
    */
  def show(uid: String) =  Action {
    val dd = new DataDriver
    val u = dd.getByUID(uid)
    if(u == null) {
      Ok(Json.toJson("No such user by uid " + uid))
    } else Ok(Json.toJson(u.toJson))
  }
  //TODO: return errors beautifully
  def delete(uid: String) = Action {
    val dd = new DataDriver
    if(dd.removeUser(uid)){
      Ok("")
    } else {
      Ok("failure")
    }
  }
}
