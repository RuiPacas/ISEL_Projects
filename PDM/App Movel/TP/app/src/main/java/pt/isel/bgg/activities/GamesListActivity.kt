package pt.isel.bgg.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.GamesListAdapter
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewModelFactory.GamesListViewModelProviderFactory
import pt.isel.bgg.viewmodel.GamesListViewModel

const val TAG: String = "BGG_APP"
const val GAME_STRING = "game"

class GamesListActivity : AppCompatActivity() {

    val adapter: GamesListAdapter by lazy {
        GamesListAdapter(model, { game ->
            val intent = Intent(this, GameDetailActivity::class.java)
            // Put Information In Intent
            intent.putExtra(GAME_STRING, game)
            startActivity(intent)
        })
    }

    val model: GamesListViewModel by lazy {
        val app = application as BGGApp
        val bggWebApiViewModelProviderFactory =
            GamesListViewModelProviderFactory(
                app,
                intent
            )
        ViewModelProviders.of(
            this,
            bggWebApiViewModelProviderFactory
        )[GamesListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listsactivity)
        val recicledView = findViewById<RecyclerView>(R.id.recyclerListActivity)
        recicledView.adapter = adapter
        recicledView.layoutManager = LinearLayoutManager(this)
        model.observe(this) {
            adapter.submitList(it)
        }
    }


}