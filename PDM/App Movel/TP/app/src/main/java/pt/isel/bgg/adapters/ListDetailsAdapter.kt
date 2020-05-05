package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.db.Game
import pt.isel.bgg.viewmodel.ListDetailsViewModel

class ListDetailsAdapter(
    private val model: ListDetailsViewModel,
    private val holderOnClickListener: (Game) -> Unit,
    private val onDeleteClickListener: (String) -> Unit
) : RecyclerView.Adapter<ListDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListDetailViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_dbgamedetail,
            parent,
            false
        ) as LinearLayout
        return ListDetailViewHolder(layout)
    }

    override fun getItemCount(): Int = model.gamesList.games.size


    override fun onBindViewHolder(holder: ListDetailViewHolder, position: Int) {
        val name: TextView = holder.listsLayout.findViewById(R.id.dbGameName)
        name.text = model.gamesList.games[position].name
        val id: TextView = holder.listsLayout.findViewById(R.id.dbGameId)
        id.text = model.gamesList.games[position].id
        val gameYear: TextView = holder.listsLayout.findViewById(R.id.dbGameYear)
        gameYear.text = model.gamesList.games[position].year_published.toString()
        val averageRating: TextView = holder.listsLayout.findViewById(R.id.dbAverageRating)
        averageRating.text = model.gamesList.games[position].average_user_rating.toString()
        val deleteButton: Button = holder.listsLayout.findViewById(R.id.dbDeleteGameButton)
        deleteButton.setOnClickListener { onDeleteClickListener(model.gamesList.games[position].id) }
        holder.listsLayout.setOnClickListener { holderOnClickListener(model.gamesList.games[position]) }
    }
}

class ListDetailViewHolder(val listsLayout: LinearLayout) : RecyclerView.ViewHolder(listsLayout) {

}
