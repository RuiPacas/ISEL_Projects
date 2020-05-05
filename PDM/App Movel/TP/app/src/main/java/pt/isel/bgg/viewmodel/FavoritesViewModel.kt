package pt.isel.bgg.viewmodel

import android.app.Application
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import pt.isel.bgg.activities.TAG
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.db.BGGDb
import pt.isel.bgg.model.favorite.*
import pt.isel.bgg.worker.WorkerFavoriteGames
import java.util.*

class FavoritesViewModel(private val bggDb: BGGDb, val application : BGGApp) : ViewModel() {
    private var liveData: LiveData<List<Favorite>> = MutableLiveData()


    val lists: List<Favorite>
        get() = liveData.value ?: emptyList()


    fun getFavoritesLists() {
        liveData = bggDb.favoriteDao().getAllFavoritesLists()
    }

    fun observe(owner: LifecycleOwner, observer: (List<Favorite>) -> Unit) {
        liveData.observe(owner, Observer { observer(it) })
    }

    fun createFavorite(
        favoriteName: String,
        publisherName: String,
        artistName: String,
        mechanics: ArrayList<Mechanic>,
        categories: ArrayList<Category>,
        url: String
    ) {
        Log.v(TAG,url)


        object : AsyncTask<Unit, Int, Unit>() {
            override fun doInBackground(vararg p0: Unit?) {
                startFavorite(favoriteName, url, publisherName, artistName, mechanics, categories)
            }

            override fun onPostExecute(result: Unit?) {
                BGGApp.bggWebApi.searchFavoriteGames(url,{
                        games ->
                    Log.v(TAG,games.size.toString())
                    bggDb.gameDao().insertGame(*games.toTypedArray())
                    bggDb.favoriteGameDao().insertFavoriteGame(*(games.map { game ->
                        FavoriteGame(
                            favoriteName,
                            game.id
                        )
                    }).toTypedArray())

                    val request : PeriodicWorkRequest = application.scheduleBackgroundWork(url,favoriteName)

                    val favoriteWorkerID = request.id.toString()

                    object : AsyncTask<Unit,Int,Unit>(){
                        override fun doInBackground(vararg params: Unit?) {
                            bggDb.favoriteDao().setWorkeriD(favoriteName,favoriteWorkerID)
                        }
                    }.execute()

                }
                ,
                    {throw it}
                )
            }
        }.execute()
    }


    fun getFavoriteAttribute(
        favoriteName: String,
        onResult: (FavoriteAttributes) -> Unit
    ) {
        object : AsyncTask<Unit, Int, FavoriteAttributes>() {
            override fun doInBackground(vararg p0: Unit?): FavoriteAttributes? {
                val syncFavoriteAttributeByName =
                    bggDb.favoriteDao().getSyncFavoriteAttributeByName(favoriteName)
                Log.v(TAG,syncFavoriteAttributeByName.publisher.publisherName)
                return syncFavoriteAttributeByName
            }

            override fun onPostExecute(result: FavoriteAttributes?) {
                onResult(result!!)
            }
        }.execute()
    }

    fun removeFavorite(favorite : Favorite) {
        Log.v(TAG,"Favorite Name : "+favorite.favoriteName + " Favorite Url: "+favorite.searchUrl + "Favorite Workid : "+ favorite.favoriteworkerid)

        object : AsyncTask< Unit, Int, Unit>(){

            override fun doInBackground(vararg params: Unit?) {
                bggDb.favoriteDao().deleteFavorite(favorite)
            }

            override fun onPostExecute(result: Unit?) {
                WorkManager.getInstance(application.applicationContext).cancelWorkById(UUID.fromString(favorite.favoriteworkerid))
            }
        }.execute()

    }

    private fun startFavorite(
        favoriteName: String,
        url: String,
        publisherName: String,
        artistName: String,
        mechanics: ArrayList<Mechanic>,
        categories: ArrayList<Category>
    ) {


        bggDb.favoriteDao().insertFavorite(Favorite(favoriteName, url,null))

        bggDb.publisherDao().insertPublisher(Publisher(publisherName))
        bggDb.favoritePublisherDao()
            .insertFavoritePublisher(FavoritePublisher(favoriteName, publisherName))

        bggDb.artistDao().insertArtist(Artist(artistName))
        bggDb.favoriteArtistDao()
            .insertFavoriteArtist(FavoriteArtist(favoriteName, artistName))

        
        for (mechanic in mechanics) {
            bggDb.mechanicDao().insertMechanic(mechanic)
            bggDb.favoriteMechanicDao().insertFavoriteMechanic(
                FavoriteMechanic(
                    favoriteName,
                    mechanic.mechanicId
                )
            )

        }

        for (category in categories) {
            bggDb.categoryDao().insertCategory(category)
            bggDb.favoriteCategoryDao().insertFavoriteCategory(
                FavoriteCategory(
                    favoriteName,
                    category.categoryId
                )
            )
        }
    }
}


