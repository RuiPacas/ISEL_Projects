package pt.isel.bgg.viewmodel

import androidx.lifecycle.*
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.model.favorite.Category

class MultipleCategoryChoicesViewModel() : ViewModel() {
    private var liveData: LiveData<List<Category>> = MutableLiveData()

    val categories: List<Category>
        get() = liveData.value ?: emptyList()

    fun getCategories() {
        BGGApp.bggWebApi.getCategories(
            { it.forEach { x -> BGGApp.bggDb.categoryDao().insertCategory(x) } },
            { throw it }
        )
        liveData = BGGApp.bggDb.categoryDao().getAllCategories()
    }

    fun observe(owner: LifecycleOwner, observer: (List<Category>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }
}