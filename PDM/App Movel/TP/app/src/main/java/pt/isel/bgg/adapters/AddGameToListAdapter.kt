package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.viewmodel.AddGameToListViewModel


class AddGameToListAdapter(
    val model: AddGameToListViewModel,
    val onAddClickListener: (String) -> Unit
) : RecyclerView.Adapter<AddGameToListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddGameToListViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_liststoaddgame,
            parent,
            false
        ) as LinearLayout
        return AddGameToListViewHolder(layout)
    }

    override fun getItemCount(): Int = model.lists.size


    override fun onBindViewHolder(holder: AddGameToListViewHolder, position: Int) {
        val textView: TextView = holder.listsLayout.findViewById(R.id.listToAddAGameName)
        val button: Button = holder.listsLayout.findViewById(R.id.addGameToListButton)
        textView.text = model.lists[position].name
        button.setOnClickListener { onAddClickListener(model.lists[position].name) }
    }

}

class AddGameToListViewHolder(val listsLayout: LinearLayout) :
    RecyclerView.ViewHolder(listsLayout) {

}
