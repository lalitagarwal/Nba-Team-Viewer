package android.sample.nbateamviewer.ui.fragment

import android.content.res.AssetManager
import android.os.Bundle
import android.sample.nbateamviewer.NbaViewerApplication
import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.ui.contract.TeamListContractor
import android.sample.nbateamviewer.ui.presenter.TeamListPresenter
import android.sample.nbateamviewer.adapter.TeamListAdapter
import android.sample.nbateamviewer.database.repository.TeamRepository
import android.sample.nbateamviewer.model.Team
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_team_list.*
import kotlinx.coroutines.Dispatchers

class TeamListFragment : Fragment(), TeamListContractor.View {
    private var presenter: TeamListContractor.Presenter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_team_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = TeamRepository((activity?.applicationContext as NbaViewerApplication).nbaViewerDatabase)
        presenter = TeamListPresenter(this@TeamListFragment, repository, Dispatchers.Main, Dispatchers.IO)

        tv_sort_name.setOnClickListener {
            if (tv_sort_name.text == getString(R.string.sort_name_asc)) {
                tv_sort_name.text = getString(R.string.sort_name_desc)
                presenter?.setComparator(
                    NAME_SORT,
                    DESC_SORT
                )
            } else {
                tv_sort_name.text = getString(R.string.sort_name_asc)
                presenter?.setComparator(
                    NAME_SORT,
                    ASC_SORT
                )
            }
        }

        tv_sort_wins.setOnClickListener {
            if (tv_sort_wins.text == getString(R.string.sort_wins_asc)) {
                tv_sort_wins.text = getString(R.string.sort_wins_desc)
                presenter?.setComparator(
                    WINS_SORT,
                    DESC_SORT
                )
            } else {
                tv_sort_wins.text = getString(R.string.sort_wins_asc)
                presenter?.setComparator(
                    WINS_SORT,
                    ASC_SORT
                )
            }
        }

        tv_sort_losses.setOnClickListener {
            if (tv_sort_losses.text == getString(R.string.sort_losses_asc)) {
                tv_sort_losses.text = getString(R.string.sort_losses_desc)
                presenter?.setComparator(
                    LOSSES_SORT,
                    DESC_SORT
                )
            } else {
                tv_sort_losses.text = getString(R.string.sort_losses_asc)
                presenter?.setComparator(
                    LOSSES_SORT,
                    ASC_SORT
                )
            }
        }

        presenter?.setList()
    }

    override fun setRecyclerView(teamList: List<Team>) {
        rv_team_list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val adapter = TeamListAdapter(context, teamList)
        rv_team_list.adapter = adapter
    }

    override fun updateListView(teamList: List<Team>) {
        (rv_team_list.adapter as TeamListAdapter).updateItems(teamList)
        rv_team_list.adapter?.notifyDataSetChanged()
    }

    override fun resetSortUI(items: List<String>) {
        for (item in items) {
            when (item) {
                NAME_SORT -> {
                    tv_sort_name.text = getString(R.string.sort_name_asc)
                }

                WINS_SORT -> {
                    tv_sort_wins.text = getString(R.string.sort_wins_asc)
                }

                LOSSES_SORT -> {
                    tv_sort_losses.text = getString(R.string.sort_losses_asc)
                }
            }
        }
    }

    override fun getAssets(): AssetManager? {
        return activity?.assets
    }

    override fun showLoader() {
        pb_loading.show()
    }

    override fun hideLoader() {
        pb_loading.hide()
    }

    override fun showToastMsg(msg: Int) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val NAME_SORT = "nameSort"
        const val WINS_SORT = "winSort"
        const val LOSSES_SORT = "lossesSort"

        const val ASC_SORT = 1
        const val DESC_SORT = 2
    }
}