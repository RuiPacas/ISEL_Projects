package pt.isel.bgg.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.FavoriteGamesAdapter
import pt.isel.bgg.viewModelFactory.FavoriteGamesViewModelProviderFactory
import pt.isel.bgg.viewmodel.FavoriteGamesViewModel


class FavoriteGamesActivity : AppCompatActivity(){

    val adapter: FavoriteGamesAdapter by lazy {
        FavoriteGamesAdapter(model, { game ->
            val intent = Intent(this, GameDetailActivity::class.java)
            // Put Information In Intent
            intent.putExtra(GAME_STRING, game)
            startActivity(intent)
        })
    }

    val model: FavoriteGamesViewModel by lazy {
        val favoriteGamesViewModelProviderFactory = FavoriteGamesViewModelProviderFactory(intent)
        ViewModelProviders.of(
            this,
            favoriteGamesViewModelProviderFactory
        )[FavoriteGamesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listsactivity)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerListActivity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        model.observe(this) {
            adapter.notifyDataSetChanged()
        }
    }

}