package pt.isel.bgg.viewModelFactory

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.activities.FAVORITE_NAME
import pt.isel.bgg.activities.REQUEST_STRING
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewmodel.FavoriteGamesViewModel

class FavoriteGamesViewModelProviderFactory(val intent: Intent) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = FavoriteGamesViewModel(
            BGGApp.bggDb,
            intent.getStringExtra(FAVORITE_NAME)!!
        )
        viewModel.getDBGamesList()
        return viewModel as T
    }
}