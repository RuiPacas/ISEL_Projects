package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["favoriteName", "artistName"], foreignKeys =[
    ForeignKey(onDelete = ForeignKey.CASCADE,entity = Favorite::class,parentColumns =["favoriteName"],childColumns = ["favoriteName"])])
class FavoriteArtist(
    val favoriteName: String,
    val artistName: String
)