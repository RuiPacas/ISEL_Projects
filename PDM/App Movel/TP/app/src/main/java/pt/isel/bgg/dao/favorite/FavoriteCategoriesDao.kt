package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.model.favorite.FavoriteCategory

@Dao
interface FavoriteCategoriesDao {

    @Query("SELECT * FROM FavoriteCategory WHERE favoriteName = :favoriteName")
    fun getFavoriteCategory(favoriteName: String): LiveData<List<FavoriteCategory>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteCategory(favoriteCategory: FavoriteCategory)
}