package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.model.favorite.FavoriteArtist

@Dao
interface FavoriteArtistsDao {

    @Query("SELECT * FROM FAVORITEARTIST WHERE favoriteName = :favoriteName")
    fun getFavoriteArtists(favoriteName: String): LiveData<List<FavoriteArtist>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteArtist(favoriteArtist: FavoriteArtist)
}