package pt.isel.bgg.viewmodel


import android.os.AsyncTask
import androidx.lifecycle.*
import pt.isel.bgg.db.BGGDb
import pt.isel.bgg.model.lists.GameList

class PersonalisedListsViewModel(private val db: BGGDb) : ViewModel() {

    private var liveData: LiveData<List<GameList>> = MutableLiveData()


    val lists: List<GameList>
        get() = liveData.value ?: emptyList()


    fun getListsNames() {
        liveData = db.listDao().getListsNames()
    }

    fun observe(owner: LifecycleOwner, observer: (List<GameList>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }

    fun addList(listName: String) {
        object : AsyncTask<Unit, Int, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                db.listDao().insertList(GameList(listName))
            }
        }.execute()

    }

    fun removeList(listName: String) {
        object : AsyncTask<Unit, Int, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                db.listDao().deleteList(GameList(listName))
            }
        }.execute()
    }


}