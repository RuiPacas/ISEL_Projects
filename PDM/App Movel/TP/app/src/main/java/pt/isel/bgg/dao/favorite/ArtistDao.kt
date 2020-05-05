package pt.isel.bgg.dao.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pt.isel.bgg.model.favorite.Artist

@Dao
interface ArtistDao {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArtist(artist: Artist)

}