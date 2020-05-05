package pt.isel.bgg.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewmodel.FavoritesViewModel

class FavoritesViewModelProviderFactory(val application : BGGApp) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = FavoritesViewModel(BGGApp.bggDb,application )
        viewModel.getFavoritesLists()
        return viewModel as T

    }
}