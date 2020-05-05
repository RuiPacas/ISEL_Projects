package pt.isel.bgg.dao.game

import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.bgg.db.Game


@Dao
interface GameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGame(vararg game: Game)


    @Delete
    fun deleteGames(vararg games: Game)

    @Query("SELECT * FROM Game")
    fun getAllGames(): LiveData<List<Game>>


}