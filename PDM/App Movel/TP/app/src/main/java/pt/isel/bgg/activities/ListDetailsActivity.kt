package pt.isel.bgg.activities


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.ListDetailsAdapter
import pt.isel.bgg.viewModelFactory.ListDetailsViewModelProviderFactory
import pt.isel.bgg.viewmodel.ListDetailsViewModel

class ListDetailsActivity : AppCompatActivity() {
    val adapter: ListDetailsAdapter by lazy {
        ListDetailsAdapter(model, { game ->
            val intent = Intent(this, GameDetailActivity::class.java)
            // Put Information In Intent
            intent.putExtra(GAME_STRING, game)
            startActivity(intent)
        }, { gameId ->
            model.removeGame(gameId)
        })
    }

    val model: ListDetailsViewModel by lazy {
        val listDetailsViewModelProviderFactory = ListDetailsViewModelProviderFactory(intent)
        ViewModelProviders.of(
            this,
            listDetailsViewModelProviderFactory
        )[ListDetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listdetailsactivity)
        val recyclerView     = findViewById<RecyclerView>(R.id.recyclerListDetailsActivity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        model.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }
}