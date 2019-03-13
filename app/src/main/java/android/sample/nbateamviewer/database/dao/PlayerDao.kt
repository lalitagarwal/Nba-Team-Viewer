package android.sample.nbateamviewer.database.dao

import android.sample.nbateamviewer.model.Player
import androidx.room.Dao
import androidx.room.Query

@Dao
interface PlayerDao : BaseDao<Player> {

    @Query("Select * from player where teamId = :teamId")
    fun getPlayersForTeam(teamId: Int): List<Player>
}