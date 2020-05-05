package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.bgg.model.favorite.Favorite
import pt.isel.bgg.model.favorite.FavoriteAttributes

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: Favorite)

    @Query("SELECT * FROM Favorite WHERE favoriteName LIKE :favoriteName")
    fun getFavoriteAttributeByName(favoriteName: String): LiveData<FavoriteAttributes>

    @Query("SELECT * FROM Favorite")
    fun getAllFavoritesLists(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE favoriteName LIKE :favoriteName")
    fun getSyncFavoriteAttributeByName(favoriteName: String): FavoriteAttributes

    @Delete
    fun deleteFavorite(favorite: Favorite)

    @Query("UPDATE favorite set favoriteworkerid = :favoriteWorkerId WHERE favoriteName = :favoriteName")
    fun setWorkeriD(favoriteName: String, favoriteWorkerId : String) : Unit

    @Query("SELECT * FROM Favorite")
    fun getSyncAllFavoritesLists(): List<Favorite>

}