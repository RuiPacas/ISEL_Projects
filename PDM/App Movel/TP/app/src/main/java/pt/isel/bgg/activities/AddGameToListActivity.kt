package pt.isel.bgg.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.AddGameToListAdapter
import pt.isel.bgg.viewModelFactory.AddGameToListViewModelProviderFactory
import pt.isel.bgg.viewmodel.AddGameToListViewModel

class AddGameToListActivity : AppCompatActivity() {

    val adapter: AddGameToListAdapter by lazy {
        AddGameToListAdapter(model) { listName ->
            model.insertGameIntoList(listName)
        }

    }

    val model: AddGameToListViewModel by lazy {
        val addGameToListViewModelProviderFactory =
            AddGameToListViewModelProviderFactory(intent)
        ViewModelProviders.of(
            this,
            addGameToListViewModelProviderFactory
        )[AddGameToListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addgametolist)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewOfAddGamesToList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        model.observe(this) {
            adapter.notifyDataSetChanged()
        }

    }


}