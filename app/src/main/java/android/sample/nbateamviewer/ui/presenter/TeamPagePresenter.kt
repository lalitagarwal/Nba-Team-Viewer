package android.sample.nbateamviewer.ui.presenter

import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.ui.contract.TeamPageContractor
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class TeamPagePresenter(
    private val view: TeamPageContractor.View,
    private val repository: TeamRepository,
    private val teamId: Int
) : TeamPageContractor.Presenter {

    private val TAG = TeamPagePresenter::class.simpleName
    private var team: Team? = null
    private var players: List<Player>? = listOf()

    override fun setList() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                view.showLoader()
                withContext(Dispatchers.IO) {
                    team = repository.getTeamForGivenId(teamId)
                    getPlayerData()
                }
                view.updateTeamData(team)
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch teams: ${e.localizedMessage}")
                view.hideLoader()
                view.showToastMsg(R.string.error_msg)
            }
        }
    }

    private fun getPlayerData() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.IO) {
                    players = repository.getPlayersForTeamId(teamId)
                }

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