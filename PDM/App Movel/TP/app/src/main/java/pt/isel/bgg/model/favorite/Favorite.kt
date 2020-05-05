package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Favorite(
    @PrimaryKey val favoriteName: String,
    val searchUrl: String,
    val favoriteworkerid : String?
)