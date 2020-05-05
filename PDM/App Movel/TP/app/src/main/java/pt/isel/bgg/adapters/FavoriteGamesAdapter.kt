package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.isel.bgg.R
import pt.isel.bgg.db.Game
import pt.isel.bgg.viewmodel.FavoriteGamesViewModel
import java.math.BigDecimal
import java.math.RoundingMode


class FavoriteGamesAdapter(
    val model: FavoriteGamesViewModel,
    val holderOnClickListener: (Game) -> Unit
) : RecyclerView.Adapter<FavoriteGamesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteGamesViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_game,
            parent,
            false
        ) as LinearLayout
        return FavoriteGamesViewHolder(layout)
    }

    override fun getItemCount(): Int = model.gamesList.size


    override fun onBindViewHolder(holder: FavoriteGamesViewHolder, position: Int) {
        val item = model.gamesList.get(position)
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
}


class FavoriteGamesViewHolder(val gamesLayout: LinearLayout) : RecyclerView.ViewHolder(gamesLayout) {

}