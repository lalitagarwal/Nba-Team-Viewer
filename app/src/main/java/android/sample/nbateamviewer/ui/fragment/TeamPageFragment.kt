package android.sample.nbateamviewer.ui.fragment

import android.os.Bundle
import android.sample.nbateamviewer.NbaViewerApplication
import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.ui.contract.TeamPageContractor
import android.sample.nbateamviewer.ui.presenter.TeamPagePresenter
import android.sample.nbateamviewer.adapter.PlayerListAdapter
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_team_page.*
import kotlinx.coroutines.Dispatchers

class TeamPageFragment : Fragment(), TeamPageContractor.View {
    private var presenter: TeamPageContractor.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val teamId = arguments?.get("teamClicked") as Int
        val repository = TeamRepository((activity?.applicationContext as NbaViewerApplication).nbaViewerDatabase)
        presenter = TeamPagePresenter(this, repository, teamId, Dispatchers.Main, Dispatchers.IO)
        presenter?.setList()
    }

    override fun setRecyclerView(playersList: List<Player>) {
        rv_team_list?.layoutManager = GridLayoutManager(context, 2, RecyclerView.HORIZONTAL, false)
        rv_team_list?.adapter = PlayerListAdapter(context, playersList)
    }

    override fun showLoader() {
        pb_loading.show()
    }

    override fun hideLoader() {
        pb_loading.hide()
    }

    override fun updateTeamData(team: Team?) {
        team?.let {
            tv_team_name.text = team.name
            tv_team_wins.text = getString(R.string.team_wins, team.wins.toString())
            tv_team_losses.text = getString(R.string.team_losses, team.losses.toString())
        }
    }

    override fun showToastMsg(msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}