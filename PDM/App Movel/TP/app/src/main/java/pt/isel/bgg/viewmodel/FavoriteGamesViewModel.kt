package pt.isel.bgg.viewmodel

import android.util.Log
import androidx.lifecycle.*
import pt.isel.bgg.activities.TAG
import pt.isel.bgg.api.BGGWebApi
import pt.isel.bgg.db.BGGDb
import pt.isel.bgg.db.Game
import pt.isel.bgg.model.favorite.FavoriteGame

class FavoriteGamesViewModel(
    private val bggDb: BGGDb,private val favoriteName: String
) : ViewModel() {

    private var liveData: LiveData<List<Game>> = MutableLiveData()
    val gamesList: List<Game>
        get() = liveData.value ?: emptyList()


    fun getDBGamesList() {
        liveData = bggDb.favoriteGameDao().getAllFavoriteGames(favoriteName)
    }


    fun observe(owner: LifecycleOwner, cb: (List<Game>) -> Unit) {
        liveData.observe(owner, Observer(cb))

    }
}