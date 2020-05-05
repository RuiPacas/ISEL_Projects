package pt.isel.bgg.viewModelFactory

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.activities.GAME_STRING
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewmodel.AddGameToListViewModel

class AddGameToListViewModelProviderFactory(private val intent: Intent) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel =
            AddGameToListViewModel(intent.getParcelableExtra(GAME_STRING)!!, BGGApp.bggDb)
        viewModel.getListsNames()
        return viewModel as T
    }
}