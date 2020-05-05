package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(primaryKeys = ["favoriteName", "mechanicId"], foreignKeys =[
    ForeignKey(onDelete = CASCADE,entity = Favorite::class,parentColumns =["favoriteName"],childColumns = ["favoriteName"])])
class FavoriteMechanic(

    val favoriteName: String,
    val mechanicId: String
)