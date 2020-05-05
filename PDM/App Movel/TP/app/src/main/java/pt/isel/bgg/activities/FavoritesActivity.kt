package pt.isel.bgg.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.FavoritesAdapter
import pt.isel.bgg.api.BGA_FAVORITES_SEARCH
import pt.isel.bgg.app.BGGApp
import pt.isel.bgg.model.favorite.Category
import pt.isel.bgg.model.favorite.FavoriteAttributes
import pt.isel.bgg.model.favorite.Mechanic
import pt.isel.bgg.viewModelFactory.FavoritesViewModelProviderFactory
import pt.isel.bgg.viewmodel.FavoritesViewModel


const val CATEGORIES_REQUEST_RESULT = 1
const val MECHANICS_REQUEST_RESULT = 2
const val RESULT = "result"
const val FAVORITE_NAME = "favorite"
class FavoritesActivity : AppCompatActivity() {

    val adapter: FavoritesAdapter by lazy {
        FavoritesAdapter(model) { processFavoriteList(it) }
    }

    private val model by lazy {
        val favoritesActivityViewModelProviderFactory =
            FavoritesViewModelProviderFactory(application as BGGApp)
        ViewModelProviders.of(
            this,
            favoritesActivityViewModelProviderFactory
        )[FavoritesViewModel::class.java]
    }

    private var mechanics: ArrayList<Mechanic> = arrayListOf()
    private var categories: ArrayList<Category> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        val favoritesNameList = findViewById<EditText>(R.id.favoriteListName)
        val publisherName = findViewById<EditText>(R.id.publisherName)
        val designerName = findViewById<EditText>(R.id.designerName)

        val mechanicsButton = findViewById<Button>(R.id.mechanics)
        val categoriesButton = findViewById<Button>(R.id.categories)
        val createFavorite = findViewById<Button>(R.id.createFavorite)

        val favoritesRecyclerView = findViewById<RecyclerView>(R.id.favoritesLists)

        favoritesRecyclerView.adapter = adapter
        favoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        mechanicsButton.setOnClickListener { openMechanicsActivity() }
        categoriesButton.setOnClickListener { openCategoriesActivity() }
        createFavorite.setOnClickListener {
            addFavoriteList(
                favoritesNameList.text.toString(),
                publisherName.text.toString(),
                designerName.text.toString()
            )
            favoritesNameList.setText("")
            publisherName.setText("")
            designerName.setText("")
        }
        model.observe(this){
            adapter.notifyDataSetChanged()
        }
    }

    private fun addFavoriteList(
        favoriteName: String,
        publisherName: String,
        designerName: String
    ) {
        if (favoriteName.isBlank())
            Toast.makeText(applicationContext, getString(R.string.favoriteToast), Toast.LENGTH_SHORT).show()
        else {
            if (canCreateFavorite(publisherName, designerName)) {
                model.createFavorite(
                    favoriteName.toUpperCase(),
                    publisherName,
                    designerName,
                    mechanics,
                    categories,
                    buildUrl(publisherName, designerName, mechanics, categories)
                )
                mechanics = arrayListOf()
                categories = arrayListOf()
            }
            else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.missingFavoriteInformation),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun canCreateFavorite(publisherName: String, designerName: String): Boolean {
        var count = 0
        if(publisherName.isNotEmpty()) count++
        if(designerName.isNotEmpty()) count++
        return (count + categories.size + mechanics.size) >= 2
    }

    private fun processFavoriteList(favoriteName: String) {
        model.getFavoriteAttribute(favoriteName,this::openGamesActivity)
    }

    fun openGamesActivity(favoriteAttributes : FavoriteAttributes) {
        val intent : Intent = Intent(this,FavoriteGamesActivity::class.java)
        intent.putExtra(FAVORITE_NAME,favoriteAttributes.favorite.favoriteName)
        startActivity(intent)
    }



    private fun openCategoriesActivity() {
        val resultIntent = Intent(this, CategoriesActivity::class.java)
        startActivityForResult(resultIntent, CATEGORIES_REQUEST_RESULT)
    }

    private fun openMechanicsActivity() {
        val resultIntent = Intent(this, MechanicsActivity::class.java)
        startActivityForResult(resultIntent, MECHANICS_REQUEST_RESULT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CATEGORIES_REQUEST_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                categories = data?.getParcelableArrayListExtra<Category>(RESULT) as ArrayList<Category>
                //Log.v("save",categoriesNames.toString())
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                categories = arrayListOf()
            }
        } else if (requestCode == MECHANICS_REQUEST_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                mechanics = data?.getParcelableArrayListExtra<Mechanic>(RESULT) as ArrayList<Mechanic>
                //Log.v("save",mechanicsNames.toString())
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                mechanics = arrayListOf()
            }
        }

    }



    private fun buildUrl(
        publisherName: String,
        designerName: String,
        mechanics: ArrayList<Mechanic>,
        categories: ArrayList<Category>
    ): String {

        val categoriesString = if(categories.isEmpty()) "" else categories.map { c -> c.categoryId+"," }.reduce{ acc, s -> acc+s}.removeSuffix(",")
        val mechanicsString = if(mechanics.isEmpty()) "" else mechanics.map{ m -> m.mechanicId+","}.reduce{ acc, s -> acc+s  }.removeSuffix(",")

        return String.format(BGA_FAVORITES_SEARCH, parseSpaces(mechanicsString), parseSpaces(categoriesString),
            parseSpaces(publisherName),
            parseSpaces(designerName))

    }


}