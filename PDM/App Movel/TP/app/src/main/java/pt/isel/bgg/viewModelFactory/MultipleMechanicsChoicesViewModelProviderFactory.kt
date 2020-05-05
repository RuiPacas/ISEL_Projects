package pt.isel.bgg.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.isel.bgg.viewmodel.MultipleMechanicsChoicesViewModel

class MultipleMechanicsChoicesViewModelProviderFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = MultipleMechanicsChoicesViewModel()
        viewModel.getMechanics()
        return viewModel as T
    }

}
