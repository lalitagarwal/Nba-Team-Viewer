package android.sample.nbateamviewer.ui.contract

import android.content.res.AssetManager
import android.sample.nbateamviewer.model.Team

class TeamListContractor {
    interface View {
        fun setRecyclerView(teamList: List<Team>)

        fun resetSortUI(items: List<String>)

        fun updateListView(teamList: List<Team>)

        fun getAssets(): AssetManager?

        fun showLoader()

        fun hideLoader()

        fun showToastMsg(msg: Int)
    }

    interface Presenter {
        fun setList()

        fun setComparator(criteria: String, sortType: Int)
    }
}