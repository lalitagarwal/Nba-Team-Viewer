package android.sample.nbateamviewer.ui.presenter

import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.ui.contract.TeamListContractor
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.ASC_SORT
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.LOSSES_SORT
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.NAME_SORT
import android.sample.nbateamviewer.ui.fragment.TeamListFragment.Companion.WINS_SORT
import android.sample.nbateamviewer.model.Team
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class TeamListPresenter(
    private val view: TeamListContractor.View,
    private val repository: TeamRepository
) :
    TeamListContractor.Presenter {

    private val TAG = TeamListPresenter::class.simpleName
    private var sortedTeams: List<Team>? = mutableListOf()
    private var sortComparator: Comparator<Team>? = null

    override fun setList() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                withContext(Dispatchers.IO) {
                    view.showLoader()
                    getTeams()
                }

                view.hideLoader()
                sortedTeams?.let {
                    view.setRecyclerView(it)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch teams: ${e.localizedMessage}")
                view.hideLoader()
                view.showToastMsg(R.string.error_msg)
            }
        }
    }

    private fun getTeams() {
        var teams = repository.getTeamsFromDB()
        if (teams.isNullOrEmpty()) {
            teams = repository.getTeamsFromJsonFile(view.getAssets())

            teams.let {
                for (team in teams) {
                    repository.addTeam(team)
                    for (player in team.players) {
                        player.teamId = team.id
                        repository.addPlayer(player)
                    }
                }
            }

        }
        sortedTeams = teams.sortedWith(compareBy { it.name })
    }

    override fun setComparator(criteria: String, sortType: Int) {
        when (criteria) {
            NAME_SORT -> {
                setNameSortComparator(sortType)
                sortList()
                view.resetSortUI(listOf(WINS_SORT, LOSSES_SORT))
            }

            WINS_SORT -> {
                setWinsSortComparator(sortType)
                sortList()
                view.resetSortUI(listOf(NAME_SORT, LOSSES_SORT))
            }

            LOSSES_SORT -> {
                setLossesSortComparator(sortType)
                sortList()
                view.resetSortUI(listOf(NAME_SORT, WINS_SORT))
            }
        }
    }

    private fun setNameSortComparator(sortType: Int) {
        sortComparator = if (sortType == ASC_SORT) {
            compareBy { it.name }
        } else {
            compareByDescending { it.name }
        }
    }

    private fun setWinsSortComparator(sortType: Int) {
        sortComparator = if (sortType == ASC_SORT) {
            compareBy { it.wins }
        } else {
            compareByDescending { it.wins }
        }
    }

    private fun setLossesSortComparator(sortType: Int) {
        sortComparator = if (sortType == ASC_SORT) {
            compareBy { it.losses }
        } else {
            compareByDescending { it.losses }
        }
    }

    private fun sortList() {
        sortedTeams?.let { teams ->
            sortComparator?.let {
                sortedTeams = teams.sortedWith(it)
            }
        }

        sortedTeams?.let {
            view.updateListView(it)
        }
    }
}