package pt.isel.bgg.viewModelFactory

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.activities.GAME_STRING
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.db.Game
import pt.isel.bgg.viewmodel.GameDetailViewModel

class GameDetailViewModelProviderFactory(val app: BGGApp, val intent: Intent) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val game = intent.getParcelableExtra<Game>(GAME_STRING)!!
        val viewModel = GameDetailViewModel(game)
        return viewModel as T
    }
}