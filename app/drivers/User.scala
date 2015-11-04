package drivers

/**
 * Created by tom on 11/1/15.
 */
import java.util.UUID
import play.api.libs.json._

/**Simple model for the user data
 *
 * @param uid UUID this should never be the same as any other
 * @param firstName
 * @param lastName
 * @param dob
 * @param dod
 */

class User(var uid: UUID = UUID.randomUUID(),
            var firstName:String,
            var lastName:String,
            var dob:Long,
            var dod:Long) {
  def this(firstName:String,
           lastName:String,
           dob:Long) = {
    this(UUID.randomUUID(), firstName,lastName,dob,0L)
  }
  def this(firstName:String,
           lastName:String,
           dob:Long,
           dod:Long) = {
    this(UUID.randomUUID(), firstName,lastName,dob,dod)
  }
  /** * Process user data to json
    *
    * @return json
    */
  def toJson: JsValue ={
    JsObject(Seq(
      "uid" -> JsString(uid.toString),
      "firstName" -> JsString(firstName),
      "lastName" -> JsString(lastName),
      "dob" -> JsString(dob.toString),
      "dod" -> JsString(dod.toString)
    ))
  }
  override def toString = {
    toJson.toString()
  }

}
