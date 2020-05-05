package pt.isel.bgg


import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.awaitility.kotlin.await
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import pt.isel.bgg.activities.PersonalisedListsActivity
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.db.Game
import pt.isel.bgg.model.favorite.*
import pt.isel.bgg.model.lists.GameList
import pt.isel.bgg.model.lists.GamesInAList
import pt.isel.bgg.model.lists.ListsGames
import java.util.concurrent.CompletableFuture

@RunWith(RobolectricTestRunner::class)
class ListDaoTest {
    @Test
    fun testInsertAndRead() {
        /**
         * AAA (Arrange Act Assert)
         * Arrange
         */
        val ctr = Robolectric.buildActivity(PersonalisedListsActivity::class.java).setup()
        val all: LiveData<List<GameList>> = BGGApp.bggDb.listDao().getListsNames()
        assertEquals(0, (all.value ?: emptyList()).size)
        val listToInsert = GameList("New List")
        /**
         * Act
         */
        println("Thread ${Thread.currentThread().hashCode()}")
        asyncListInsert(listToInsert).get() // Wait for Insert completion
        /**
         * Assert
         */
        val finish = CompletableFuture<Unit>()
        all.observe(ctr.get(), Observer {
            if (it != null && it.size != 0) {
                println("Entrou aqui")
                assertEquals("New List", it[0].name)
                finish.complete(null)
            }
        })
        await.pollInSameThread().until {
            Robolectric.flushForegroundThreadScheduler()
            finish.isDone
        }
    }

    @Test
    fun testInsertAGameIntoListAndReadIt() {
        /**
         * Arrange
         */
        val ctr = Robolectric.buildActivity(PersonalisedListsActivity::class.java).setup()


        val gameLists: LiveData<List<GameList>> = BGGApp.bggDb.listDao().getListsNames()
        val games: LiveData<List<Game>> = BGGApp.bggDb.gameDao().getAllGames()
        val listsGames: LiveData<List<ListsGames>> = BGGApp.bggDb.listDao().allListsGames()

        val listToInsert = GameList("New List")
        val gameToInsert = Game(
            "PDMGAME",
            "pdm",
            0,
            0,
            0,
            0,
            "xpto",
            "xpto_url",
            "PDMTeacher",
            arrayOf(),
            0.0,
            null,
            null
        )
        val listsGameToInsert =
            ListsGames("New List", "PDMGAME")

        /**
         * Act
         */
        asyncListInsert(listToInsert).get()
        asyncGameInsert(gameToInsert).get()
        asyncGameIntoListInsert(listsGameToInsert).get()

        var finish = CompletableFuture<Unit>()

        gameLists.observe(ctr.get(), Observer {
            if (it != null && it.size != 0) {
                println("Entrou aqui - List")
                assertEquals("New List", it[0].name)
            }
        })

        games.observe(ctr.get(), Observer {
            if (it != null && it.size != 0) {
                println("Entrou aqui - Game")
                assertEquals("PDMGAME", it[0].id)
            }
        })

        listsGames.observe(ctr.get(), Observer {
            if (it != null && it.size != 0) {
                println("Entrou aqui - ListsGames")
                assertEquals("PDMGAME", it[0].gameId)
                assertEquals("New List", it[0].listName)
            }
        })

        val gamesOfAList: LiveData<GamesInAList> =
            BGGApp.bggDb.gameListDao().getAllGamesIdOfAList("New List")
        val gameToInsert2 = Game(
            "XPTOGAME",
            "pdm",
            0,
            0,
            0,
            0,
            "xpto",
            "xpto_url",
            "PDMTeacher",
            arrayOf(),
            0.0,
            null,
            null
        )
        val listsGameToInsert2 =
            ListsGames("New List", "XPTOGAME")
        asyncGameInsert(gameToInsert2).get()
        asyncGameIntoListInsert(listsGameToInsert2).get()
        finish = CompletableFuture<Unit>()

        gamesOfAList.observe(ctr.get(), Observer {
            if (it != null && it.games.size > 1) {
                println("Entrou aqui - Games Of A List " + it.games[1].id)
                assertEquals(2, it.games.size)
                finish.complete(null)
            }
        })

        await.pollInSameThread().until {
            Robolectric.flushForegroundThreadScheduler()
            finish.isDone
        }


    }

    @Test
    fun testFavorites() {
        /**
         * Arrange
         */
        val ctr = Robolectric.buildActivity(PersonalisedListsActivity::class.java).setup()

        val artistToInsert = Artist("Artist1")
        val categoryToInsert = Category("Id1", "Category1")
        val categoryToInsert2 = Category("Id2", "Category2")
        val favoriteToInsert = Favorite("Favorite", "xpto.foo.com")
        val mechanicToInsert = Mechanic("Id2", "Mechanic1")
        val publisherToInsert = Publisher("Publisher1")
        val favoriteArtistToInsert = FavoriteArtist("Favorite", "Artist1")
        val favoriteCategoryToInsert = FavoriteCategory("Favorite", "Category1")
        val favoriteCategoryToInsert2 = FavoriteCategory("Favorite", "Category2")
        val favoriteMechanictToInsert = FavoriteMechanic("Favorite", "Mechanic1")
        val favoritePublisherToInsert = FavoritePublisher("Favorite", "Publisher1")
        /**
         * Act
         */
        asyncInsert(artistToInsert, BGGApp.bggDb.artistDao()::insertArtist).get()
        asyncInsert(categoryToInsert, { c ->
            BGGApp.bggDb.categoryDao().insertCategory(c)

        }).get()
        asyncInsert(favoriteToInsert, BGGApp.bggDb.favoriteDao()::insertFavorite).get()
        asyncInsert(mechanicToInsert, BGGApp.bggDb.mechanicDao()::insertMechanic).get()
        asyncInsert(publisherToInsert, BGGApp.bggDb.publisherDao()::insertPublisher).get()
        asyncInsert(publisherToInsert, BGGApp.bggDb.publisherDao()::insertPublisher).get()
        asyncInsert(
            favoriteArtistToInsert,
            BGGApp.bggDb.favoriteArtistDao()::insertFavoriteArtist
        ).get()
        asyncInsert(
            favoriteCategoryToInsert,
            BGGApp.bggDb.favoriteCategoryDao()::insertFavoriteCategory
        ).get()
        asyncInsert(
            favoriteMechanictToInsert,
            BGGApp.bggDb.favoriteMechanicDao()::insertFavoriteMechanic
        ).get()
        asyncInsert(
            favoritePublisherToInsert,
            BGGApp.bggDb.favoritePublisherDao()::insertFavoritePublisher
        ).get()


        var finish = CompletableFuture<Unit>()


        val gamesOfAList: LiveData<FavoriteAttributes> =
            BGGApp.bggDb.favoriteDao().getFavoriteAttributeByName("Favorite")

        gamesOfAList.observe(ctr.get(), Observer {
            if (it != null) {
                println(
                    "Entrou aqui" +
                            "Artist Name = " +
                            "Mechanic Name = " + it.mechanics.get(0).mechanicName +
                            "Category Name = " + it.categories.get(0).categoryName +
                            "Publisher Name = " + it.publisher.publisherName
                )

                assertEquals("Artist1", it.artist.artistName)
                assertEquals("Mechanic1", it.mechanics.get(0).mechanicName)
                assertEquals("Category1", it.categories.get(0).categoryName)
                assertEquals("Publisher1", it.publisher.publisherName)

                finish.complete(null)
            }
        })

        await.pollInSameThread().until {
            Robolectric.flushForegroundThreadScheduler()
            finish.isDone
        }

        finish = CompletableFuture<Unit>()
        asyncInsert(categoryToInsert2, { c ->
            BGGApp.bggDb.categoryDao().insertCategory(c)

        }).get()
        asyncInsert(
            favoriteCategoryToInsert2,
            BGGApp.bggDb.favoriteCategoryDao()::insertFavoriteCategory
        ).get()
        gamesOfAList.observe(ctr.get(), Observer {
            if (it != null && it.categories.size > 1) {
                println(
                    "Entrou aqui " +
                            "Artist Name = " + it.artist.artistName +
                            " Mechanic Name = " + it.mechanics.get(0).mechanicName +
                            " Category Name = " + it.categories.get(1).categoryName +
                            " Publisher Name = " + it.publisher.publisherName
                )

                assertEquals("Artist1", it.artist.artistName)
                assertEquals("Mechanic1", it.mechanics.get(0).mechanicName)
                assertEquals(2, it.categories.size)
                assertEquals("Publisher1", it.publisher.publisherName)
                finish.complete(null)
            }
        })

        await.pollInSameThread().until {
            Robolectric.flushForegroundThreadScheduler()
            finish.isDone
        }


    }
}


fun asyncGameIntoListInsert(listsGameToInsert: ListsGames) =
    CompletableFuture.supplyAsync {
        println("Thread ${Thread.currentThread().hashCode()}")
        BGGApp.bggDb.gameListDao().insertGameIntoList(listsGameToInsert)
    }

fun asyncListInsert(list: GameList) =
    CompletableFuture.supplyAsync {
        println("Thread ${Thread.currentThread().hashCode()}")
        BGGApp.bggDb.listDao().insertList(list)
    }

fun asyncGameInsert(game: Game) =
    CompletableFuture.supplyAsync {
        println("Thread ${Thread.currentThread().hashCode()}")
        BGGApp.bggDb.gameDao().insertGame(game)
    }

fun <T> asyncInsert(t: T, cb: (T) -> (Unit)) =
    CompletableFuture.supplyAsync {
        println("Slave Thread ${Thread.currentThread().hashCode()}")
        cb(t)
    }