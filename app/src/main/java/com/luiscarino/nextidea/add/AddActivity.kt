package com.luiscarino.nextidea.add

import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.list.model.room.entity.Category
import com.luiscarino.nextidea.list.model.room.entity.Idea
import com.luiscarino.nextidea.list.model.room.entity.Status
import com.luiscarino.nextidea.list.viewmodel.IdeaViewModel
import com.luiscarino.nextidea.util.toButtonColor
import com.luiscarino.nextidea.util.toDrawableId
import kotlinx.android.synthetic.main.activity_add.*
import org.koin.android.ext.android.inject

/**
 * Activity that handles Ideas add and edit mode.
 */
class AddActivity : AppCompatActivity() {

    companion object {
        const val NO_ID_PROVIDED = -1L
        private const val INTENT_ARG_ID = "ARG_IDEA_ID"

        fun getIntentInEditMode(ideaId: Long?, packageContext: Context): Intent {
            val intent = Intent(packageContext, AddActivity::class.java)
            intent.putExtra(INTENT_ARG_ID, ideaId)
            return intent
        }

        fun getIntentAddMode(packageContext: Context): Intent {
            return Intent(packageContext, AddActivity::class.java)
        }
    }


    private val ideaViewModel: IdeaViewModel by inject()
    // should I move this to eh view model??
    private lateinit var categories: List<Category>
    private lateinit var status: List<Status>
    private var selectedStatusPosition = 0
    private lateinit var selectedCategory: Category
    private lateinit var selectedStatus: Status

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ideaId = intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED)


        setContentView(R.layout.activity_add)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_twotone_keyboard_arrow_left)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ideaViewModel.getAllCategories()?.observe(this,
                Observer<List<Category>> { c ->
                    categories = c!!
                    selectedCategory = categories[0]
                    val selectedCategoryTile = selectedCategory.categoryTitle
                    categoryNameTextView.text = selectedCategoryTile
                    categoryIcon.setImageResource(toDrawableId(selectedCategoryTile))
                })

        ideaViewModel.getAllStatus()?.observe(this,
                Observer<List<Status>> { s ->
                    status = s!!
                    selectedStatus = status[0] // default to first status in list
                    selectedStatusPosition = 0
                    statusTextView.text = selectedStatus.statusTitle
                })

        statusTextView.setOnClickListener {
            moveToNextStatus()
        }

        categoryNameTextView.setOnClickListener {
            openCategorySelection()
        }

        categoryIcon.setOnClickListener {
            openCategorySelection()
        }


        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)


        saveButton.setOnClickListener {

            ideaViewModel.insert(Idea(titleTextView.text.toString(),
                    descriptionTextView.text.toString(),
                    selectedStatus,
                    selectedCategory
            ))
            finish()
        }

    }

    private fun getStatusPositionById(statusId: Long?): Long {
        for (s in status) {
            if (statusId == s.statusId) {
                return s.statusId
            }
        }
        return -1
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun moveToNextStatus() {
        if (selectedStatusPosition == status.size - 1) {
            selectedStatusPosition = 0
        } else {
            selectedStatusPosition++
        }
        selectedStatus = status[selectedStatusPosition]
        val selectedStatusTitle = selectedStatus.statusTitle
        statusTextView.text = selectedStatusTitle
        statusTextView.setBackgroundColor(resources.getColor(toButtonColor(selectedStatusTitle)))
    }

    private fun openCategorySelection() {
        val b = AlertDialog.Builder(this)
        b.setTitle(getString(R.string.category_dialog_title))
        val categoriesList: Array<CharSequence> = categories.map { category -> category.categoryTitle as CharSequence }.toTypedArray()
        b.setItems(categoriesList) { _, which ->
            selectedCategory = categories[which]
            updateSelectedCategory(selectedCategory)
        }
        b.show()
    }


    private fun updateSelectedCategory(category: Category) {
        val categoryTitle = category.categoryTitle
        categoryNameTextView.text = categoryTitle
        categoryIcon.setImageResource(toDrawableId(categoryTitle))
    }

}
