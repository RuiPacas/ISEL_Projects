package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.model.favorite.Mechanic
import pt.isel.bgg.viewmodel.MultipleMechanicsChoicesViewModel

class MultipleMechanicsChoicesAdapter(
    private val model: MultipleMechanicsChoicesViewModel
) : RecyclerView.Adapter<MultipleMechanicsChoicesViewHolder>() {

    val checkedList : ArrayList<Mechanic> = arrayListOf()
    fun getCheckList() = checkedList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultipleMechanicsChoicesViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_choices,
            parent,
            false
        ) as LinearLayout
        return MultipleMechanicsChoicesViewHolder(layout)
    }

    override fun getItemCount(): Int = model.mechanics.size


    override fun onBindViewHolder(holder: MultipleMechanicsChoicesViewHolder, position: Int) {
        val choice: CheckBox = holder.listsLayout.findViewById(R.id.choice)
        val choiceName = model.mechanics.get(position).mechanicName
        val choiceId = model.mechanics.get(position).mechanicId
        choice.text = choiceName
        choice.isChecked = checkedList.contains(Mechanic(choiceId,choiceName))
        choice.setOnClickListener{
            val check : Boolean = choice.isChecked
            if(check) checkedList.add(Mechanic(choiceId,choiceName))
            else checkedList.remove(Mechanic(choiceId,choiceName))
        }}
}

class MultipleMechanicsChoicesViewHolder(val listsLayout: LinearLayout) :
    RecyclerView.ViewHolder(listsLayout) {

}
    