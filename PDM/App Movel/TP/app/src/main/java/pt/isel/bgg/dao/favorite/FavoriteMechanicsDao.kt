package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.model.favorite.FavoriteMechanic

@Dao
interface FavoriteMechanicsDao {

    @Query("SELECT * FROM FavoriteMechanic WHERE favoriteName = :favoriteName")
    fun getFavoriteMechanics(favoriteName: String): LiveData<List<FavoriteMechanic>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteMechanic(favoriteMechanic: FavoriteMechanic)

}