package pt.isel.bgg.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewmodel.PersonalisedListsViewModel


class PersonalisedListsViewModelProviderFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = PersonalisedListsViewModel(BGGApp.bggDb)
        viewModel.getListsNames()
        return viewModel as T
    }
}