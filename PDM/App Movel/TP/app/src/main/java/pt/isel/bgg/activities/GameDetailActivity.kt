package pt.isel.bgg.activities

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import pt.isel.bgg.R
import pt.isel.bgg.api.BGA_ARTIST_GAMES
import pt.isel.bgg.api.BGA_COMPANY_GAMES
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.viewModelFactory.GameDetailViewModelProviderFactory
import pt.isel.bgg.viewmodel.GameDetailViewModel
import java.math.BigDecimal
import java.math.RoundingMode


const val SAVE_IMAGE_DIALOG = "SAVE_IMAGE_DIALOG"
class GameDetailActivity : AppCompatActivity() {

    val model: GameDetailViewModel by lazy {
        val app = application as BGGApp
        val bggWebApiViewModelProviderFactory =
            GameDetailViewModelProviderFactory(
                app,
                intent
            )
        ViewModelProviders.of(
            this,
            bggWebApiViewModelProviderFactory
        )[GameDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gamedetail)
        val gameImage = findViewById<ImageView>(R.id.gameImage)
        val nameTextView = findViewById<TextView>(R.id.gameName)
        val gameYear = findViewById<TextView>(R.id.gameYear)
        val minNumberPlayers = findViewById<TextView>(R.id.minNumberPlayers)
        val maxNumberPlayers = findViewById<TextView>(R.id.maxNumberPlayers)
        val minimumAge = findViewById<TextView>(R.id.minimumAge)
        val description = findViewById<TextView>(R.id.description)
        val primaryPublisher = findViewById<TextView>(R.id.primaryPublisher)
        val gameCreators: TableLayout = findViewById<TableLayout>(R.id.gameCreators)
        val averageRate = findViewById<TextView>(R.id.averageRate)
        val gameRules = findViewById<TextView>(R.id.gameRules)
        val addGame = findViewById<Button>(R.id.selectListToAdd)

        setImage(gameImage, model.game.image_url!!)
        setImageListeners(gameImage)

        nameTextView.text = model.game.name
        gameYear.text = model.game.year_published.toString()
        minNumberPlayers.text = model.game.min_players.toString()
        maxNumberPlayers.text = model.game.max_players.toString()
        minimumAge.text = model.game.min_age.toString()
        description.text = model.game.description
        primaryPublisher.text = model.game.primary_publisher

        primaryPublisher.setOnClickListener {
            val intent = Intent(this, GamesListActivity::class.java)
            val completedUrl = String.format(BGA_COMPANY_GAMES, model.game.primary_publisher)
            intent.putExtra(REQUEST_STRING, completedUrl)
            startActivity(intent)
        }

        model.game.artists?.forEach { artistName ->
            val tr: TableRow = TableRow(this)
            val tv: TextView = TextView(this)
            tr.setOnClickListener {
                val intent = Intent(this, GamesListActivity::class.java)
                val completedUrl = String.format(BGA_ARTIST_GAMES, artistName)
                intent.putExtra(REQUEST_STRING, completedUrl)
                startActivity(intent)
            }
            tv.text = artistName
            tr.addView(tv)
            gameCreators.addView(tr)
        }

        averageRate.text =
            BigDecimal(model.game.average_user_rating).setScale(1, RoundingMode.HALF_UP).toDouble()
                .toString()

        gameRules.text = model.game.rules_url
        Linkify.addLinks(gameRules, Linkify.WEB_URLS)
        addGame.setOnClickListener { showLists() }
    }

    private fun setImageListeners(gameImage: ImageView) {

       val myGestureDetector = GestureDetector(this,
            object : GestureDetector.OnGestureListener {
                override fun onSingleTapUp(e: MotionEvent): Boolean {

                    return false
                }

                override fun onShowPress(e: MotionEvent) {
                }

                override fun onScroll(
                    e1: MotionEvent, e2: MotionEvent,
                    distanceX: Float, distanceY: Float
                ): Boolean {

                    return true
                }

                override fun onLongPress(e: MotionEvent) {

                    val drawable = gameImage.getDrawable() as BitmapDrawable
                    val bitmap = drawable.bitmap
                    CheckSaveImage(bitmap,{
                        Toast
                            .makeText(applicationContext,getString(R.string.sucessSavingPicture),Toast.LENGTH_SHORT)
                            .show()
                    },
                        {
                            Toast
                                .makeText(applicationContext,getString(R.string.InsucessSavingPicture),Toast.LENGTH_SHORT)
                                .show()
                        }
                        ).show(supportFragmentManager,SAVE_IMAGE_DIALOG)
                }

                override fun onFling(
                    e1: MotionEvent, e2: MotionEvent,
                    velocityX: Float, velocityY: Float
                ): Boolean {
                    return true
                }

                override fun onDown(e: MotionEvent): Boolean {
                    return false
                }
            })

        val gestureListener = OnTouchListener { v, event ->

            if (myGestureDetector.onTouchEvent(event)) {
                return@OnTouchListener true
            }
            false
        }
        gameImage.setOnClickListener{
            if (model.game.official_url != null && !model.game.official_url!!.isBlank()) {
                val uri = Uri.parse(model.game.official_url)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.noOfficialUrl),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        gameImage.setOnTouchListener(gestureListener)
    }

    private fun showLists() {
        val intent = Intent(this, AddGameToListActivity::class.java)
        intent.putExtra(GAME_STRING, model.game)
        startActivity(intent)
    }


    private fun setImage(gameImage: ImageView, image_url: String) {
        Glide
            .with(this)
            .load(image_url)
            .placeholder(R.mipmap.ic_launcher)
            .into(gameImage)

    }
}