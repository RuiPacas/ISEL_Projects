package pt.isel.bgg.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.*
import pt.isel.bgg.db.BGGDb
import pt.isel.bgg.db.Game
import pt.isel.bgg.model.lists.GameList
import pt.isel.bgg.model.lists.ListsGames


class AddGameToListViewModel(private val game: Game, private val db: BGGDb) : ViewModel() {

    private var liveData: LiveData<List<GameList>> = MutableLiveData()
    val lists: List<GameList>
        get() = liveData.value ?: emptyList()


    fun getListsNames() {
        liveData = db.listDao().getListsNamesWithoutTheGame(game.id)
    }

    fun insertGameIntoList(listName: String) {
        object : AsyncTask<Unit, Int, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                db.gameDao().insertGame(game)
                db.gameListDao().insertGameIntoList(
                    ListsGames(
                        listName,
                        game.id
                    )
                )
            }
        }.execute()
    }

    fun observe(owner: LifecycleOwner, cb: (List<GameList>) -> Unit) {
        liveData.observe(owner, Observer(cb))
    }
}