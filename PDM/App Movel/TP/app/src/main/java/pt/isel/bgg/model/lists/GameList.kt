package pt.isel.bgg.model.lists

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GameList(
    @PrimaryKey val name: String
)