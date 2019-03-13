package android.sample.nbateamviewer.ui.presenter

import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.ui.contract.TeamPageContractor
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.util.Log
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class TeamPagePresenter(
    private val view: TeamPageContractor.View,
    private val repository: TeamRepository,
    private val teamId: Int,
    private val mainContext: CoroutineContext,
    private val IOContext: CoroutineContext
) : TeamPageContractor.Presenter {

    private val TAG = TeamPagePresenter::class.simpleName
    private var team: Team? = null
    private var players: List<Player>? = listOf()

    override fun setList() {
        val teamsDeferred: Deferred<Team> = CoroutineScope(IOContext).async {
            repository.getTeamForGivenId(teamId)
        }

        CoroutineScope(mainContext).launch {
            view.showLoader()
            try {
                team = teamsDeferred.await()
                view.hideLoader()
                view.updateTeamData(team)
                getPlayerData()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch teams: ${e.localizedMessage}")
                view.hideLoader()
                view.showToastMsg(R.string.error_msg)
            }
        }
    }

    private fun getPlayerData() {
        val playerDeferred: Deferred<List<Player>> = CoroutineScope(IOContext).async {
            repository.getPlayersForTeamId(teamId)
        }

        CoroutineScope(mainContext).launch {
            view.showLoader()
            try {
                players = playerDeferred.await()
                players?.let {
                    view.hideLoader()
                    view.setRecyclerView(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch players: ${e.localizedMessage}")
                view.hideLoader()
                view.showToastMsg(R.string.error_msg)
            }
        }
    }

}