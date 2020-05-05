package pt.isel.bgg.dataSource

import android.util.Log
import androidx.paging.PositionalDataSource
import pt.isel.bgg.activities.TAG
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.db.Game
import pt.isel.bgg.dto.GameDto


class GameDataSource(private val url: String) : PositionalDataSource<Game>() {
    var pageCount = 1
    val api by lazy {
        BGGApp.bggWebApi
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Game>) {
        Log.v(TAG, "***** LOADING PAGE " + ++pageCount)
        Log.v(TAG, "page " + pageCount + " startposition " + params.startPosition)
        Log.v(TAG, "page " + pageCount + " loadSize " + params.loadSize)
        val skip: Int = params.startPosition
        val limit: Int = params.loadSize
        showData(url, limit, skip) {
            callback.onResult(it)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Game>) {
        val skip = 0
        val limit: Int = params.requestedLoadSize
        Log.v(TAG, "requested load size " + limit)
        Log.v(TAG, "***** LOADING INITIAL")
        showData(url, limit, skip) {
            callback.onResult(it, 0)
        }
    }

    fun showData(
        url: String,
        limit: Int,
        skip: Int,
        callback: (List<Game>) -> Unit
    ) {
        api.searchGames(url,
            limit,
            skip,
            { searchDto ->
                callback(searchDto.games.map { mapGameDtoToGame(it) })
            },
            {
                throw it
            }
        )
    }


}

fun mapGameDtoToGame(gameDto: GameDto): Game = Game(
    gameDto.id!!,
    gameDto.name,
    gameDto.year_published,
    gameDto.min_players,
    gameDto.max_players,
    gameDto.min_age,
    gameDto.description,
    gameDto.image_url,
    gameDto.primary_publisher,
    gameDto.artists,
    gameDto.average_user_rating,
    gameDto.rules_url,
    gameDto.official_url
)