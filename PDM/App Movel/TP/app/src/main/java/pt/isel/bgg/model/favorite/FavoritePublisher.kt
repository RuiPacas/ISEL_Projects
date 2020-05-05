package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["favoriteName", "publisherName"], foreignKeys =[
    ForeignKey(onDelete = ForeignKey.CASCADE,entity = Favorite::class,parentColumns =["favoriteName"],childColumns = ["favoriteName"])])
class FavoritePublisher(
    val favoriteName: String,
    val publisherName: String
)