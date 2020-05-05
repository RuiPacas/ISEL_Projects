package pt.isel.bgg.dao.lists

import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.bgg.model.lists.GamesInAList
import pt.isel.bgg.model.lists.ListsGames

@Dao
interface ListsGamesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGameIntoList(listGame: ListsGames)

    @Query("SELECT * FROM GameList WHERE name LIKE :listName")
    fun getAllGamesIdOfAList(listName: String): LiveData<GamesInAList>

    @Delete
    fun removeGameFromList(game: ListsGames)
}