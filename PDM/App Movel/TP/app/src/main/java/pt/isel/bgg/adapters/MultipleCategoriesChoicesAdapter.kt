package pt.isel.bgg.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.model.favorite.Category
import pt.isel.bgg.viewmodel.MultipleCategoryChoicesViewModel

class MultipleCategoriesChoicesAdapter(
    private val model: MultipleCategoryChoicesViewModel
) : RecyclerView.Adapter<MultipleCategoriesChoicesViewHolder>() {

    private val checkedList : ArrayList<Category> = arrayListOf()

    fun getCheckList() = checkedList

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultipleCategoriesChoicesViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.general_choices,
            parent,
            false
        ) as LinearLayout
        return MultipleCategoriesChoicesViewHolder(layout)
    }

    override fun getItemCount(): Int = model.categories.size


    override fun onBindViewHolder(holder: MultipleCategoriesChoicesViewHolder, position: Int) {
        val choice: CheckBox = holder.listsLayout.findViewById(R.id.choice)
        val choiceName = model.categories.get(position).categoryName
        val choiceId = model.categories.get(position).categoryId
        choice.isChecked = checkedList.contains(Category(choiceId,choiceName))
        choice.text = choiceName
        choice.setOnClickListener{
            val check : Boolean = choice.isChecked
            if(check) checkedList.add(Category(choiceId,choiceName))
            else checkedList.remove(Category(choiceId,choiceName))
        }
    }


}

class MultipleCategoriesChoicesViewHolder(val listsLayout: LinearLayout) :
    RecyclerView.ViewHolder(listsLayout) {

}
