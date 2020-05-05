package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity (primaryKeys = ["favoriteName", "gameId"], foreignKeys =[
    ForeignKey(onDelete = ForeignKey.CASCADE,entity = Favorite::class,parentColumns =["favoriteName"],childColumns = ["favoriteName"])])
class FavoriteGame (
    val favoriteName : String,
    val gameId : String)