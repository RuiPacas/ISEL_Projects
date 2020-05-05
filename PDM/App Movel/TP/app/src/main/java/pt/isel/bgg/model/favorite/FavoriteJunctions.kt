package pt.isel.bgg.model.favorite

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import pt.isel.bgg.db.Game

class FavoriteAttributes(

    @Embedded
    val favorite: Favorite,
    @Relation(
        parentColumn = "favoriteName",
        entity = Publisher::class,
        entityColumn = "publisherName",
        associateBy = Junction(
            value = FavoritePublisher::class,
            parentColumn = "favoriteName",
            entityColumn = "publisherName"
        )
    )
    val publisher: Publisher,

    @Relation(
        parentColumn = "favoriteName",
        entity = Mechanic::class,
        entityColumn = "mechanicId",
        associateBy = Junction(
            value = FavoriteMechanic::class,
            parentColumn = "favoriteName",
            entityColumn = "mechanicId"
        )
    )
    val mechanics: List<Mechanic>,

    @Relation(
        parentColumn = "favoriteName",
        entity = Category::class,
        entityColumn = "categoryId",
        associateBy = Junction(
            value = FavoriteCategory::class,
            parentColumn = "favoriteName",
            entityColumn = "categoryId"
        )
    )
    val categories: List<Category>,


    @Relation(
        parentColumn = "favoriteName",
        entity = Artist::class,
        entityColumn = "artistName",
        associateBy = Junction(
            value = FavoriteArtist::class,
            parentColumn = "favoriteName",
            entityColumn = "artistName"
        )
    )
    val artist: Artist,

    @Relation(
        parentColumn = "favoriteName",
        entity = Game::class,
        entityColumn = "id",
        associateBy = Junction(
            value = FavoriteGame::class,
            parentColumn = "favoriteName",
            entityColumn = "gameId"
        )
    )
    val games: List<Game>
)