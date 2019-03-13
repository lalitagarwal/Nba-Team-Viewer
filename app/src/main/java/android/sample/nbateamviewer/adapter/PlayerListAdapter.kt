package android.sample.nbateamviewer.adapter

import android.content.Context
import android.sample.nbateamviewer.R
import android.sample.nbateamviewer.model.Player
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_player_layout.view.*

class PlayerListAdapter(
    private var context: Context?,
    private var itemList: List<Player>?
) : RecyclerView.Adapter<PlayerListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_player_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val player = itemList?.get(position)
        player?.let {
            context?.let {
                Glide.with(it).load(R.drawable.img_person).into(holder.ivPlayerIcon)
            }

            holder.tvName.text = context?.getString(R.string.player_name, player.firstName, player.lastName)
            holder.tvPosition.text = context?.getString(R.string.player_position, player.position)
            holder.tvNumber.text = context?.getString(R.string.player_number, player.number.toString())
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivPlayerIcon: ImageView = view.iv_player_img
        var tvName: TextView = view.tv_player_name
        var tvPosition: TextView = view.tv_player_position
        var tvNumber: TextView = view.tv_player_number
    }
}