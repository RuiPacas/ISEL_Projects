package pt.isel.bgg.dao.lists

import androidx.lifecycle.LiveData
import androidx.room.*
import pt.isel.bgg.model.lists.GameList
import pt.isel.bgg.model.lists.ListsGames

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertList(list: GameList)

    @Delete
    fun deleteList(list: GameList)

    @Query("SELECT * FROM GameList ")
    fun getListsNames(): LiveData<List<GameList>>

    @Query("SELECT * FROM GameList EXCEPT SELECT listName FROM  ListsGames WHERE :gameId  = gameId")
    fun getListsNamesWithoutTheGame(gameId: String): LiveData<List<GameList>>

    @Query("SELECT * FROM ListsGames")
    fun allListsGames(): LiveData<List<ListsGames>>

}