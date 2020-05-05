package pt.isel.bgg.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.PersonalisedListAdapter
import pt.isel.bgg.viewModelFactory.PersonalisedListsViewModelProviderFactory
import pt.isel.bgg.viewmodel.PersonalisedListsViewModel

const val LIST_STRING = "LISTNAME"

class PersonalisedListsActivity : AppCompatActivity() {


    val adapter: PersonalisedListAdapter by lazy {
        PersonalisedListAdapter(model) { openListDetailActivity(it) }
    }

    private val model by lazy {
        val personalisedListsViewModelProviderFactory =
            PersonalisedListsViewModelProviderFactory()
        ViewModelProviders.of(
            this,
            personalisedListsViewModelProviderFactory
        )[PersonalisedListsViewModel::class.java]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personalisedlists)
        val listNameEditText = findViewById<EditText>(R.id.listName)
        val addButton = findViewById<Button>(R.id.createList)
        val removeButton = findViewById<Button>(R.id.deleteList)
        val listsRecycledView = findViewById<RecyclerView>(R.id.showLists)
        listsRecycledView.adapter = adapter
        listsRecycledView.layoutManager = LinearLayoutManager(this)
        model.observe(this) {
            adapter.notifyDataSetChanged()
        }

        addButton.setOnClickListener { addList(listNameEditText.text) }
        removeButton.setOnClickListener { removeList(listNameEditText.text) }
    }

    private fun openListDetailActivity(listName: String) {

        val intent = Intent(this, ListDetailsActivity::class.java)
        // Put Information In Intent
        intent.putExtra(LIST_STRING, listName)
        startActivity(intent)
    }


    private fun removeList(listName: Editable?) {
        if (listName.toString().isBlank()) Toast.makeText(
            applicationContext,
            getString(R.string.listToast),
            Toast.LENGTH_SHORT
        ).show()
        else model.removeList(listName.toString().toUpperCase())
    }

    private fun addList(listName: Editable?) {
        if (listName.toString().isBlank()) Toast.makeText(
            applicationContext,
            getString(R.string.listToast),
            Toast.LENGTH_SHORT
        ).show()
        else model.addList(listName.toString().toUpperCase())
    }


}