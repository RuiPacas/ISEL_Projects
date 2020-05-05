package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(primaryKeys = ["favoriteName", "categoryId"], foreignKeys =[
    ForeignKey(onDelete = ForeignKey.CASCADE,entity = Favorite::class,parentColumns =["favoriteName"],childColumns = ["favoriteName"])])
class FavoriteCategory(
    val favoriteName: String,
    val categoryId: String
)