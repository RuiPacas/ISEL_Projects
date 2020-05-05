package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.viewmodel.PersonalisedListsViewModel

class PersonalisedListAdapter(
    val model: PersonalisedListsViewModel,
    val holderOnClickListener: (String) -> Unit
) : RecyclerView.Adapter<PersonalisedListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalisedListViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_list,
            parent,
            false
        ) as LinearLayout
        return PersonalisedListViewHolder(layout)
    }

    override fun getItemCount(): Int = model.lists.size


    override fun onBindViewHolder(holder: PersonalisedListViewHolder, position: Int) {
        val button: Button = holder.listsLayout.findViewById(R.id.listNameButton)
        button.text = model.lists[position].name
        button.setOnClickListener { holderOnClickListener(button.text.toString()) }
    }

}

class PersonalisedListViewHolder(val listsLayout: LinearLayout) :
    RecyclerView.ViewHolder(listsLayout) {

}
