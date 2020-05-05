package pt.isel.bgg

import com.google.gson.Gson
import org.junit.Test

import pt.isel.bgg.dto.SearchDto
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun GetMockText() {
        val url =
            "https://www.boardgameatlas.com/api/search?name=Catan&pretty=true&client_id=sCfolwMJNj&limit=10"
        var gson = Gson()
        var search = gson.fromJson(
            BufferedReader(InputStreamReader(URL(url).openStream())),
            SearchDto::class.java
        )
        search.games.forEach { println(it.toString() + ",") }
    }

    @Test
    fun TestReduce() {
        val listOf = listOf<String>("abc,", "123", "xpto,", "Portugal")
        val str = listOf.reduce { x, s -> x + s }
        println(str)
    }
}
