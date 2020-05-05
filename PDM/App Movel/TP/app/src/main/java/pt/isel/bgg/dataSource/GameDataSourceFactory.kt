package pt.isel.bgg.dataSource

import androidx.paging.DataSource
import pt.isel.bgg.db.Game

class GameDataSourceFactory(private val url: String) : DataSource.Factory<Int, Game>() {


    override fun create(): DataSource<Int, Game> {
        return GameDataSource(url)
    }


}