package android.sample.nbateamviewer.adapter

import android.content.Context
import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.model.Team
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_team_layout.view.*

class TeamListAdapter(
    private var context: Context?,
    private var itemList: List<Team>?
) : RecyclerView.Adapter<TeamListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_team_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val team = itemList?.get(position)
        team?.let {
            holder.tvName.text = team.name
            holder.tvWins.text = context?.getString(R.string.team_wins, team.wins.toString())
            holder.tvLosses.text = context?.getString(R.string.team_losses, team.losses.toString())

            val bundle = bundleOf("teamClickedId" to team.id)
            holder.itemView.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_team_list_to_team_page, bundle)
            )
        }
    }

    fun updateItems(teams: List<Team>) {
        itemList = listOf()
        itemList = teams
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.tv_team_name
        var tvWins: TextView = view.tv_team_wins
        var tvLosses: TextView = view.tv_team_losses
    }
}