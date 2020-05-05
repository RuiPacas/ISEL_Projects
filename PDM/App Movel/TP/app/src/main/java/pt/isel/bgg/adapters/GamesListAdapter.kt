package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.isel.bgg.R
import pt.isel.bgg.db.Game
import pt.isel.bgg.viewmodel.GamesListViewModel
import java.math.BigDecimal
import java.math.RoundingMode

class GamesListAdapter(
    val model: GamesListViewModel,
    val holderOnClickListener: (Game) -> Unit
) :
    PagedListAdapter<Game, GamesListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamesListViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_game,
            parent,
            false
        ) as LinearLayout
        return GamesListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: GamesListViewHolder, position: Int) {
        val item = getItem(position)
        holder.gamesLayout.findViewById<TextView>(R.id.gameYear).text =
            if (item!!.name!!.length > 40) item.name!!.take(40) + "..."
            else item.name
        holder.gamesLayout.findViewById<TextView>(R.id.gameId).text = item.id
        holder.gamesLayout.findViewById<TextView>(R.id.averageRating).text =
            BigDecimal(item.average_user_rating).setScale(1, RoundingMode.HALF_UP).toDouble()
                .toString()
        Glide
            .with(holder.gamesLayout.context)
            .load(item.image_url)
            .placeholder(R.mipmap.ic_launcher)
            .into(holder.gamesLayout.findViewById(R.id.gameImage) as ImageView)

        holder.itemView.setOnClickListener { holderOnClickListener(item) }

    }


    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Game>() {
            override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean =
                oldItem == newItem

        }
    }

}


class GamesListViewHolder(val gamesLayout: LinearLayout) : RecyclerView.ViewHolder(gamesLayout) {

}