

/**
 * Created by tom on 11/1/15.
 */
package drivers

import java.sql.ResultSet
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
  def getByUID(uid: Long): Map[String, String] ={
    val conn = DB.getConnection("default")
    val s = conn.prepareStatement("SELECT * FROM OBJECTS WHERE UID = ?")
    s.setLong(1,uid)
    val rs: ResultSet = s.executeQuery()
    var out = Map[String, String]()
    while(rs.next()){
      out = out + ("UID" -> rs.getString("UID"))
      out = out + ("FIRSTNAME" -> rs.getString("FIRSTNAME"))
      out = out + ("LASTNAME" -> rs.getString("LASTNAME"))
      out = out + ("DOB" -> rs.getString("DOB"))
      out = out + ("DOD" -> rs.getString("DOD"))
    }
    conn.close()
    out
  }

  /**Gets all database content by UID
   *
   * @return Map[String,String] query results
   */
  def getAll: Map[String, String] = {
    var out = Map[String, String]()
    val conn = DB.getConnection("default")
    val s = conn.createStatement()
    val rs: ResultSet = s.executeQuery("SELECT UID FROM OBJECTS")//
    while(rs.next()){
      out = out + ("URL" -> ("/api/objects/"+rs.getString("UID")))
    }
    out
  }
  def addUser = {
    val conn = DB.getConnection("default")
    val s = conn.prepareStatement("SELECT UID, FIRSTNAME, LASTNAME, DOB, DOD FROM OBJECTS WHERE UID ?")
  }
  def removeUser(uid: Long): Boolean = {
    val conn = DB.getConnection("default")
    val s = conn.prepareStatement("DELETE FROM OBJECTS WHERE UID = ?")
    s.setLong(1,uid)
    s.execute()
    val c = conn.prepareStatement("SELECT UID FROM OBJECTS WHERE UID = ?")
    c.setLong(1,uid)
    val rs: ResultSet = c.executeQuery()
    if(rs.next()) {
      conn.close()
      return false
    }
    else {
      conn.close()
      return true
    }
  }
}
