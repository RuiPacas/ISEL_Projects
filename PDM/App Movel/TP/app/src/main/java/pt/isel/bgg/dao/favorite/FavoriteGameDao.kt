package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.db.Game
import pt.isel.bgg.model.favorite.FavoriteGame

@Dao
interface FavoriteGameDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteGame(vararg favoriteGame : FavoriteGame)

    @Query("SELECT G.* FROM FavoriteGame INNER JOIN Game AS G ON gameId = g.id WHERE favoriteName = :favoriteName" )
    fun getAllFavoriteGames(favoriteName : String) : LiveData<List<Game>>

    @Query("SELECT COUNT(gameId) FROM FavoriteGame WHERE favoriteName = :favoriteName")
    fun getRowCount(favoriteName: String) : Int

}