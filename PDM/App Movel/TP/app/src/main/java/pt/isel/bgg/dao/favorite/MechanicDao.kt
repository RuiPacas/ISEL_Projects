package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.model.favorite.Mechanic

@Dao
interface MechanicDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMechanic(mechanic: Mechanic)

    @Query("SELECT * FROM Mechanic")
    fun getAllMechanics(): LiveData<List<Mechanic>>
}