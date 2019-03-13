package android.sample.nbateamviewer.database.repository

import android.content.res.AssetManager
import android.sample.nbateamviewer.database.NbaViewerDatabase
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import com.beust.klaxon.Klaxon
import java.io.InputStreamReader

open class TeamRepository(private val nbaViewerDatabase: NbaViewerDatabase) {

    fun getTeamsFromDB(): List<Team> {
        return nbaViewerDatabase.teamDao().getTeams()
    }

    fun getTeamsFromJsonFile(assetManager: AssetManager?): List<Team> {
        assetManager?.let {
            return Klaxon().parseArray(InputStreamReader(assetManager.open("input.json"))) ?: listOf()
        } ?: return listOf()
    }

    fun getTeamForGivenId(teamId: Int): Team {
        return nbaViewerDatabase.teamDao().getTeamForGivenId(teamId)
    }

    fun addTeam(team: Team) {
        nbaViewerDatabase.teamDao().insert(team)
    }

    fun addPlayer(player: Player) {
        nbaViewerDatabase.playerDao().insert(player)
    }

    fun getPlayersForTeamId(teamId: Int): List<Player> {
        return nbaViewerDatabase.playerDao().getPlayersForTeam(teamId)
    }
}