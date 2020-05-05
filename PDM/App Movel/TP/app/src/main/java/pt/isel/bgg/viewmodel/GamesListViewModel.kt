package pt.isel.bgg.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.toLiveData
import pt.isel.bgg.dataSource.GameDataSourceFactory
import pt.isel.bgg.db.Game

class GamesListViewModel(
    private val url: String
) : ViewModel() {
    private val config: PagedList.Config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(30)
        .setPageSize(30)
        .setPrefetchDistance(30)
        .build()
    private val liveData: LiveData<PagedList<Game>> = GameDataSourceFactory(url).toLiveData(config)
    val games: PagedList<Game>
        get() = liveData.value!!


    fun observe(owner: LifecycleOwner, cb: (PagedList<Game>) -> Unit) {
        liveData.observe(owner, Observer(cb))

    }
}