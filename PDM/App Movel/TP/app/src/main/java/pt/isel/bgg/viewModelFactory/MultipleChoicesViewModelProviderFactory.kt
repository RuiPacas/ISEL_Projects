package pt.isel.bgg.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.viewmodel.MultipleCategoryChoicesViewModel


class MultipleCategoriesChoicesViewModelProviderFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = MultipleCategoryChoicesViewModel()
        viewModel.getCategories()
        return viewModel as T
    }
}