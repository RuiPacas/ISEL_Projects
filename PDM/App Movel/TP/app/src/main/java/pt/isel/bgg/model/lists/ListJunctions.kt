package pt.isel.bgg.model.lists

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import pt.isel.bgg.db.Game


class GamesInAList(
    @Embedded
    val gameList: GameList,
    @Relation(
        parentColumn = "name",
        entity = Game::class,
        entityColumn = "id",
        associateBy = Junction(
            value = ListsGames::class,
            parentColumn = "listName",
            entityColumn = "gameId"
        )
    )
    val games: List<Game>
)


class ListsWithAGame(
    @Embedded
    val game: Game,
    @Relation(
        parentColumn = "id",
        entity = GameList::class,
        entityColumn = "name",
        associateBy = Junction(
            value = ListsGames::class,
            parentColumn = "gameId",
            entityColumn = "listName"
        )
    )
    val lists: List<GameList>
)



