package pt.isel.bgg.model.lists

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["listName", "gameId"], foreignKeys =[
    ForeignKey(onDelete = ForeignKey.CASCADE,entity = GameList::class,parentColumns =["name"],childColumns = ["listName"])])
class ListsGames(
    val listName: String,
    val gameId: String
)