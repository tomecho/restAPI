

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
  def getByUID(uid: Long): Map[String, String] ={
    val conn = DB.getConnection("default")
    val s = conn.prepareStatement("SELECT UID, FIRSTNAME, LASTNAME, DOB, DOD FROM OBJECTS WHERE UID ?")
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
  def getAll: Map[String, String] = {
    var out = Map[String, String]()
    val conn = DB.getConnection("default")
    val s = conn.createStatement()
    val rs: ResultSet = s.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES")//SELECT UID FROM OBJECTS
    while(rs.next()){
      println(rs.getString(3))
      //out = out + ("URL" -> ("/api/objects/"+rs.getString("UID")))
    }
    out
  }
}
