package pt.isel.bgg.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.*
import pt.isel.bgg.db.BGGDb
import pt.isel.bgg.model.lists.GameList
import pt.isel.bgg.model.lists.GamesInAList
import pt.isel.bgg.model.lists.ListsGames

class ListDetailsViewModel(private val bggDb: BGGDb, private val gameList: GameList) : ViewModel() {

    private var liveData: LiveData<GamesInAList> = MutableLiveData()
    val gamesList: GamesInAList
        get() = liveData.value ?: GamesInAList(
            gameList,
            emptyList()
        )


    fun observe(owner: LifecycleOwner, cb: (GamesInAList) -> Unit) {
        liveData.observe(owner, Observer(cb))
    }

    fun getGamesList() {
        liveData = bggDb.gameListDao().getAllGamesIdOfAList(gameList.name)
    }

    fun removeGame(gameId: String) {
        object : AsyncTask<Unit, Int, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                bggDb.gameListDao().removeGameFromList(
                    ListsGames(
                        gameList.name,
                        gameId
                    )
                )


                //check if there's no more lists with this game, then delete it from DB
            }
        }.execute()
    }
}