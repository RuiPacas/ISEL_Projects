package pt.isel.bgg.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pt.isel.bgg.dao.favorite.*
import pt.isel.bgg.dao.game.GameDao
import pt.isel.bgg.dao.lists.ListDao
import pt.isel.bgg.dao.lists.ListsGamesDao
import pt.isel.bgg.model.favorite.*
import pt.isel.bgg.model.lists.GameList
import pt.isel.bgg.model.lists.ListsGames

@Database(
    entities = arrayOf(
        Game::class,
        GameList::class,
        ListsGames::class,
        Artist::class,
        Category::class,
        Favorite::class,
        FavoriteArtist::class,
        FavoriteCategory::class,
        FavoriteMechanic::class,
        FavoritePublisher::class,
        Mechanic::class,
        Publisher::class,
        FavoriteGame::class
    ), version = 1
)

@TypeConverters(pt.isel.bgg.TypeConverters::class)
abstract class BGGDb : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun listDao(): ListDao
    abstract fun gameListDao(): ListsGamesDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun artistDao(): ArtistDao
    abstract fun categoryDao(): CategoryDao
    abstract fun mechanicDao(): MechanicDao
    abstract fun publisherDao(): PublisherDao
    abstract fun favoriteArtistDao(): FavoriteArtistsDao
    abstract fun favoriteCategoryDao(): FavoriteCategoriesDao
    abstract fun favoriteMechanicDao(): FavoriteMechanicsDao
    abstract fun favoritePublisherDao(): FavoritePublisherDao
    abstract fun favoriteGameDao () : FavoriteGameDao
}