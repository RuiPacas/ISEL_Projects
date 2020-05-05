package pt.isel.bgg.dao.favorite

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.isel.bgg.model.favorite.Category

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(vararg category: Category)

    @Query("SELECT * FROM category")
    fun getAllCategories(): LiveData<List<Category>>

}