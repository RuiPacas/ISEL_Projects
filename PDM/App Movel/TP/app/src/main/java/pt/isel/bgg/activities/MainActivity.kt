package pt.isel.bgg.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pt.isel.bgg.R
import pt.isel.bgg.api.BGA_ARTIST_GAMES
import pt.isel.bgg.api.BGA_COMPANY_GAMES
import pt.isel.bgg.api.BGA_POPULAR_GAMES
import pt.isel.bgg.api.BGA_SEARCH_GAME

/**
 *  Using the official documentation, we will restore the Activities from the intent
 *
 *  "Additionally, when you open an activity from an intent,
 *  the bundle of extras is delivered to the activity both when the configuration
 *  changes and when the system restores the activity. If a piece of UI state data,
 *  such as a search query, were passed in as an intent extra when the activity was launched,
 *  you could use the extras bundle instead of the onSaveInstanceState() bundle"
 *  https://developer.android.com/topic/libraries/architecture/saving-states
 **/


const val REQUEST_STRING = "Request"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchText = findViewById<EditText>(R.id.searchText)
        val searchGameButton = findViewById<Button>(R.id.searchGames)
        val searchCompanyGamesButton = findViewById<Button>(R.id.searchCompanyGames)
        val searchGameArtistButton = findViewById<Button>(R.id.searchGameArtist)
        val searchPopularGamesButton = findViewById<Button>(R.id.popularGames)
        val personalisedGamesListButton = findViewById<Button>(R.id.personalisedGamesList)
        val favoritesButton = findViewById<Button>(R.id.favorites)
        val about = findViewById<ImageButton>(R.id.about)
        about.setOnClickListener { openAboutActivity() }
        searchGameButton.setOnClickListener { openSearchGamesActivity(searchText.text) }
        searchCompanyGamesButton.setOnClickListener { openCompanyGamesButtonActivity(searchText.text) }
        searchGameArtistButton.setOnClickListener { openGameArtistButtonActivity(searchText.text) }
        searchPopularGamesButton.setOnClickListener { openSearchPopularGamesButtonActivity() }
        personalisedGamesListButton.setOnClickListener { openPersonalisedGamesListActivity() }
        favoritesButton.setOnClickListener { openFavoritesActivity() }
    }

    private fun openFavoritesActivity() {
        val intent = Intent(this, FavoritesActivity::class.java)
        startActivity(intent)
    }

    private fun openPersonalisedGamesListActivity() {
        val intent = Intent(this, PersonalisedListsActivity::class.java)
        startActivity(intent)
    }

    private fun openSearchGamesActivity(text: Editable) {
        if (text.toString().isBlank()) {
            showToast(getString(R.string.searchGamesToast))
        } else {
            val intent = Intent(this, GamesListActivity::class.java)
            val completedUrl = String.format(BGA_SEARCH_GAME, parseSpaces(text.toString()))
            intent.putExtra(REQUEST_STRING, completedUrl)
            startActivity(intent)
        }
    }

    private fun openCompanyGamesButtonActivity(text: Editable) {
        if (text.toString().isBlank()) {
            showToast(getString(R.string.searchCompanyToast))
        } else {
            val intent = Intent(this, GamesListActivity::class.java)
            val completedUrl = String.format(BGA_COMPANY_GAMES, parseSpaces(text.toString()))
            intent.putExtra(REQUEST_STRING, completedUrl)
            startActivity(intent)
        }

    }

    private fun openGameArtistButtonActivity(text: Editable) {
        if (text.toString().isBlank()) {
            showToast(getString(R.string.searchArtistToast))
        } else {
            val intent = Intent(this, GamesListActivity::class.java)
            val completedUrl = String.format(BGA_ARTIST_GAMES, parseSpaces(text.toString()))
            intent.putExtra(REQUEST_STRING, completedUrl)
            startActivity(intent)
        }

    }


    private fun openSearchPopularGamesButtonActivity() {
        val intent = Intent(this, GamesListActivity::class.java)
        val completedUrl = BGA_POPULAR_GAMES
        intent.putExtra(REQUEST_STRING, completedUrl)
        startActivity(intent)
    }

    private fun openAboutActivity() {
        val intent = Intent(this, AboutActivity::class.java)
        startActivity(intent)
    }

    fun showToast(msg: String, context: Context = applicationContext) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}

fun parseSpaces(text: String): String {
    return text.replace(" ", "%20")
}
