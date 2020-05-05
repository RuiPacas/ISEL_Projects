package pt.isel.bgg.viewModelFactory

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.activities.REQUEST_STRING
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewmodel.GamesListViewModel

class GamesListViewModelProviderFactory(val app: BGGApp, val intent: Intent) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = GamesListViewModel(intent.getStringExtra(REQUEST_STRING)!!)
        return viewModel as T
    }
}