package drivers

/**
 * Created by tom on 11/1/15.
 */
import java.util.UUID

/**Simple model for the user data
 *
 * @param uid UUID this should never be the same as any other
 * @param firstName
 * @param lastName
 * @param dob
 * @param dod
 */

class User(uid: UUID = UUID.randomUUID(),
            firstName:String = "",
            lastName:String = "",
            dob:Long = 0L,
            dod:Long = 0L) {



}
