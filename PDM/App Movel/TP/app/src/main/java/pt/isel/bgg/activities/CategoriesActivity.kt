package pt.isel.bgg.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pt.isel.bgg.R
import pt.isel.bgg.adapters.MultipleCategoriesChoicesAdapter
import pt.isel.bgg.viewModelFactory.MultipleCategoriesChoicesViewModelProviderFactory
import pt.isel.bgg.viewmodel.MultipleCategoryChoicesViewModel



class CategoriesActivity : AppCompatActivity() {

    val adapter: MultipleCategoriesChoicesAdapter by lazy {
        MultipleCategoriesChoicesAdapter(model)
    }

    private val model by lazy {
        val multipleChoicesViewModelProviderFactory =
            MultipleCategoriesChoicesViewModelProviderFactory()
        ViewModelProviders.of(
            this,
            multipleChoicesViewModelProviderFactory
        )[MultipleCategoryChoicesViewModel::class.java]

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiplechoices)
        val saveButton: Button = findViewById(R.id.saveButton)
        saveButton.setOnClickListener { saveChoices() }

        val multipleChoicesRecyclerView = findViewById<RecyclerView>(R.id.choicesList)

        multipleChoicesRecyclerView.adapter = adapter
        multipleChoicesRecyclerView.layoutManager = LinearLayoutManager(this)

        model.observe(this) {
            adapter.notifyDataSetChanged()
        }

    }

    private fun saveChoices() {
        val returnIntent = Intent()
        returnIntent.putParcelableArrayListExtra(RESULT,adapter.getCheckList())
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}