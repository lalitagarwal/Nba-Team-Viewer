package android.sample.nbateamviewer.ui.contract

import android.sample.nbateamviewer.model.Player
import android.sample.nbateamviewer.model.Team

class TeamPageContractor {
    interface View {
        fun updateTeamData(team: Team?)

        fun setRecyclerView(playersList: List<Player>)

        fun showLoader()

        fun hideLoader()

        fun showToastMsg(msg: Int)
    }

    interface Presenter {
        fun setList()
    }
}