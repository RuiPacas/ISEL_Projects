package pt.isel.bgg.viewModelFactory

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.activities.LIST_STRING
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.model.lists.GameList
import pt.isel.bgg.viewmodel.ListDetailsViewModel


class ListDetailsViewModelProviderFactory(private val intent: Intent) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = ListDetailsViewModel(
            BGGApp.bggDb,
            GameList(intent.getStringExtra(LIST_STRING)!!)
        )
        viewModel.getGamesList()
        return viewModel as T
    }
}