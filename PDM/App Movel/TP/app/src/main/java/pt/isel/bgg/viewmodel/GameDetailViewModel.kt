package pt.isel.bgg.viewmodel

import androidx.lifecycle.ViewModel
import pt.isel.bgg.db.Game

data class GameDetailViewModel(private val gameDto: Game) : ViewModel() {
    val game
        get() = gameDto
}



