package pt.isel.bgg.api

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import pt.isel.bgg.activities.TAG
import pt.isel.bgg.dataSource.mapGameDtoToGame
import pt.isel.bgg.db.Game
import pt.isel.bgg.dto.CategorySearchDto
import pt.isel.bgg.dto.GameDto
import pt.isel.bgg.dto.MechanicsSearchDto
import pt.isel.bgg.dto.SearchDto
import pt.isel.bgg.model.favorite.Category
import pt.isel.bgg.model.favorite.Mechanic

const val BGA_CLIENT_ID = "sCfolwMJNj"
const val BGA_HOST = "https://www.boardgameatlas.com/api/"
const val BGA_SEARCH = "${BGA_HOST}search?client_id=${BGA_CLIENT_ID}"
const val BGA_SEARCH_GAME = "$BGA_SEARCH&name=%s"
const val BGA_COMPANY_GAMES = "$BGA_SEARCH&publisher=%s"
const val BGA_ARTIST_GAMES = "$BGA_SEARCH&artist=%s"
const val BGA_POPULAR_GAMES = "$BGA_SEARCH&order_by=popularity"
const val BGA_MECHANICS = "${BGA_HOST}game/mechanics?client_id=${BGA_CLIENT_ID}"
const val BGA_CATEGORIES = "${BGA_HOST}game/categories?client_id=${BGA_CLIENT_ID}"
const val BGA_FAVORITES_SEARCH ="${BGA_SEARCH}&mechanics=%s&categories=%s&publisher=%s&designer=%s"



class BGGWebApi(ctx: Context) {

    // Instantiate the RequestQueue.
    val queue = Volley.newRequestQueue(ctx)
    val gson = Gson()

    fun searchGames(
        url: String,
        limit: Int,
        skip: Int,
        onSuccess: (SearchDto) -> Unit,
        onError: (VolleyError) -> Unit
    ) {
        // Request a string response from the provided URL.
        Log.v(TAG,"Recieved URL : "+url )
        val newUrl: String = url + "&limit="+limit+"&skip="+skip
        Log.v(TAG, "*****Fetching the url : " + newUrl)

        val stringRequest = StringRequest(
            Request.Method.GET,
            newUrl,
            Response.Listener<String> { response ->
                object : AsyncTask<String, Int, SearchDto>() {
                    override fun doInBackground(vararg resp: String): SearchDto {
                        return gson.fromJson<SearchDto>(resp.get(0), SearchDto::class.java)
                    }

                    override fun onPostExecute(result: SearchDto) {
                        onSuccess(result)
                    }
                }.execute(response)
            },
            Response.ErrorListener { err -> onError(err) })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

    fun getCategories(onSuccess: (List<Category>) -> Unit, onError: (VolleyError) -> Unit) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            BGA_CATEGORIES,
            Response.Listener<String> { response ->
                object : AsyncTask<String, Int, CategorySearchDto>() {
                    override fun doInBackground(vararg resp: String): CategorySearchDto {
                        return gson.fromJson<CategorySearchDto>(
                            resp.get(0),
                            CategorySearchDto::class.java
                        )
                    }

                    override fun onPostExecute(result: CategorySearchDto) {
                        object : AsyncTask<Unit, Int, Unit>() {
                            override fun doInBackground(vararg params: Unit?) {
                                onSuccess(result.categories.map { Category(it.id,it.name) })
                            }
                        }.execute()

                    }
                }.execute(response)
            },
            Response.ErrorListener { err -> onError(err) })
        queue.add(stringRequest)
    }

    fun getMechanics(onSuccess: (List<Mechanic>) -> Unit, onError: (VolleyError) -> Unit) {
        val stringRequest = StringRequest(
            Request.Method.GET,
            BGA_MECHANICS,
            Response.Listener<String> { response ->
                object : AsyncTask<String, Int, MechanicsSearchDto>() {
                    override fun doInBackground(vararg resp: String): MechanicsSearchDto {
                        return gson.fromJson<MechanicsSearchDto>(
                            resp.get(0),
                            MechanicsSearchDto::class.java
                        )
                    }

                    override fun onPostExecute(result: MechanicsSearchDto) {
                        object : AsyncTask<Unit, Int, Unit>() {
                            override fun doInBackground(vararg params: Unit?) {
                                onSuccess(result.mechanics.map { Mechanic(it.id,it.name) })
                            }
                        }.execute()
                    }
                }.execute(response)
            },
            Response.ErrorListener { err -> onError(err) })
        queue.add(stringRequest)
    }

    fun searchFavoriteGames(url: String, onSucess: (List<Game>) -> Unit, onError: (VolleyError) -> Unit) : Unit {

        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                object : AsyncTask<String, Int, SearchDto>() {
                    override fun doInBackground(vararg resp: String): SearchDto {
                        return gson.fromJson<SearchDto>(resp.get(0), SearchDto::class.java)
                    }

                    override fun onPostExecute(result: SearchDto) {
                        object : AsyncTask<Unit, Int, Unit>() {
                            override fun doInBackground(vararg params: Unit?) {
                                onSucess(result.games.map { mapGameDtoToGame(it)})
                            }
                        }.execute()
                    }
                }.execute(response)
            },
            Response.ErrorListener { err -> onError(err) })
        queue.add(stringRequest)
    }


}
