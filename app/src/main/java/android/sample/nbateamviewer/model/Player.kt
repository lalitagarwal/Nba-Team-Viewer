package android.sample.nbateamviewer.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.beust.klaxon.Json

@Entity(tableName = "player")
data class Player(
    @Json(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @Json(ignored = true)
    var teamId: Int = 0,
    @Json(name = "first_name")
    var firstName: String = "",
    @Json(name = "last_name")
    var lastName: String = "",
    @Json(name = "position")
    var position: String = "",
    @Json(name = "number")
    var number: Int = 0
)