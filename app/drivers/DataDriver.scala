

/**
 * Created by tom on 11/1/15.
 */
package drivers

import java.sql.ResultSet
import java.util.UUID
import controllers.routes
import play.api.db.DB
import play.api.routing.Router
import play.api.Play.current

class DataDriver {
  /** Searches database by uid
   *
   * @param uid
   * @return Map[String,String] query results
   */
  def getByUID(uid: String): User ={
    val conn = DB.getConnection("default")
    try {
      val s = conn.prepareStatement("SELECT * FROM OBJECTS WHERE UID = ?")
      s.setString(1, uid)
      val rs: ResultSet = s.executeQuery()
      var out = Map[String, String]()
      while (rs.next()) {
        out = out + ("FIRSTNAME" -> rs.getString("FIRSTNAME"))
        out = out + ("LASTNAME" -> rs.getString("LASTNAME"))
        out = out + ("DOB" -> rs.getString("DOB"))
        out = out + ("DOD" -> rs.getString("DOD"))
        out = out + ("UID" -> rs.getString("UID"))
      }
      new User(UUID.fromString(out.get("UID").get),
        out.get("FIRSTNAME").get,
        out.get("LASTNAME").get,
        out.get("DOB").get.toLong,
        out.get("DOD").get.toLong)
    } catch {
      case n: NoSuchElementException => return null
    } finally {
      conn.close()
    }
  }

  /**Gets all database content by UID
   *
   * @return Map[String,String] query results
   */
  def getAll: Vector[String] = {
    var out = Vector[String]()
    val conn = DB.getConnection("default")
    val s = conn.createStatement()
    val rs: ResultSet = s.executeQuery("SELECT UID FROM OBJECTS")//
    while(rs.next()){
      out = out :+ ("/api/objects/"+rs.getString("UID"))
    }
    out
  }

  /** * Add user to database then return user
    *
    * @param user User
    * @return returns user for convenience
    */
  def addUser(user: User): User = {
    if(getByUID(user.uid.toString) != null) return null //check if uid is already in use, which it should never be
    val conn = DB.getConnection("default")
    try {
      val s = conn.prepareStatement("INSERT INTO OBJECTS VALUES(? , ? , ? , ? , ?)")
      s.setString(1, user.firstName)
      s.setString(2, user.lastName)
      s.setLong(3, user.dob)
      s.setLong(4, user.dod)
      s.setString(5, user.uid.toString)
      s.execute()
      if (getByUID(user.uid.toString).uid.equals(user.uid)) return user //success
      else {
        println("failure to add user, could not find it in db")
        return null //failure
      }
    } catch {
      case e: Exception =>
        println(user.toString)
        e.printStackTrace()
    } finally {
      conn.close
    }
    return user
  }

  /** * remove the user from database
    *
    * @param uid of the user
    * @return boolean of success
    */
  def removeUser(uid: String): Boolean = {
    val conn = DB.getConnection("default")
    try {
      val s = conn.prepareStatement("DELETE FROM OBJECTS WHERE UID = ?")
      s.setString(1, uid)
      s.execute()
      val c = conn.prepareStatement("SELECT UID FROM OBJECTS WHERE UID = ?")
      c.setString(1, uid)
      val rs: ResultSet = c.executeQuery()
      if (rs.next()) return false
      else return true
    } finally {
      conn.close()
    }
  }
}
