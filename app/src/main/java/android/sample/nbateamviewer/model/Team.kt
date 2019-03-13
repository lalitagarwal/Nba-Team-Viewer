package android.sample.nbateamviewer.model

import androidx.room.*
import com.beust.klaxon.Json
import java.io.Serializable

@Entity(tableName = "team")
data class Team(
    @Json(name = "id")
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @Json(name = "full_name")
    @ColumnInfo(name = "full_name")
    var name: String = "",
    @Json(name = "wins")
    @ColumnInfo(name = "wins")
    var wins: Int = 0,
    @Json(name = "losses")
    @ColumnInfo(name = "losses")
    var losses: Int = 0,
    @Ignore
    @Json(name = "players")
    var players: List<Player>
) : Serializable {
    constructor() : this(
        0, "", 0, 0, listOf()
    )
}