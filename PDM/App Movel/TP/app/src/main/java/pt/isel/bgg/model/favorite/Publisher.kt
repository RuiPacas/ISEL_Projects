package pt.isel.bgg.model.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Publisher(
    @PrimaryKey val publisherName: String
)