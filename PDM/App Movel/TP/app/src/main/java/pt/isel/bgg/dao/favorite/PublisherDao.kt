package pt.isel.bgg.dao.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import pt.isel.bgg.model.favorite.Publisher

@Dao
interface PublisherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPublisher(publisher: Publisher)

}