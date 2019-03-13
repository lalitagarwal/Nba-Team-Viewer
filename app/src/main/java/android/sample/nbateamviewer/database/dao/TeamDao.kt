package android.sample.nbateamviewer.database.dao

import android.sample.nbateamviewer.model.Team
import androidx.room.Dao
import androidx.room.Query

@Dao
interface TeamDao : BaseDao<Team> {

    @Query("Select * from team")
    fun getTeams(): List<Team>

    @Query("Select * from team where id = :teamId")
    fun getTeamForGivenId(teamId: Int): Team
}