package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Artist(
    @PrimaryKey val artistName: String
)