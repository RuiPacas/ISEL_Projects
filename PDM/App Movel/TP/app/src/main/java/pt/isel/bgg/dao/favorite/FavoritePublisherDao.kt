package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.model.favorite.FavoritePublisher

@Dao
interface FavoritePublisherDao {
    @Query("SELECT * FROM FavoritePublisher WHERE favoriteName = :favoriteName")
    fun getFavoritePublisher(favoriteName: String): LiveData<FavoritePublisher>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoritePublisher(favoritePublisher: FavoritePublisher)
}