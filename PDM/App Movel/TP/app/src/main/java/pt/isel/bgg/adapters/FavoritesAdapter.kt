package pt.isel.bgg.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.activities.TAG
import pt.isel.bgg.viewmodel.FavoritesViewModel

class FavoritesAdapter(
    val model: FavoritesViewModel,
    val holderOnClickListener: (String) -> Unit
) : RecyclerView.Adapter<FavoriteListViewHolder
        >() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteListViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_favorites,
            parent,
            false
        ) as LinearLayout
        return FavoriteListViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return model.lists.size
    }

    override fun onBindViewHolder(holder: FavoriteListViewHolder, position: Int) {
        val button: Button = holder.listsLayout.findViewById(R.id.favoriteButton)
        val deleteFavorite : Button = holder.listsLayout.findViewById(R.id.deleteButton)
        button.text = model.lists[position].favoriteName
        button.setOnClickListener {
            Log.v(TAG,"Favorite "+model.lists[position].favoriteName+" Button Clicked ")
            holderOnClickListener(model.lists[position].favoriteName)
        }
        deleteFavorite.setOnClickListener{
            model.removeFavorite(model.lists[position])
        }
    }
}

class FavoriteListViewHolder(val listsLayout: LinearLayout) : RecyclerView.ViewHolder(listsLayout) {

}