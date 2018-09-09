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
import java.util.*

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        setupToolbar()
        getCategoriesAndSetupView()
        getStatusAndSetupView()

        val extraIdeaId = intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED)
        ideaViewModel.isEditMode = extraIdeaId != NO_ID_PROVIDED

        if (ideaViewModel.isEditMode) {
            ideaViewModel.getIdeaById(extraIdeaId)
                    ?.observe(this, Observer {
                        ideaViewModel.selectedStatus = it?.status
                        ideaViewModel.selectedCategory = it?.category
                        updateCategoryView(ideaViewModel.selectedCategory)
                        updateStatusView(ideaViewModel.selectedStatus)
                        titleTextView.setText(it?.title)
                        descriptionTextView.setText(it?.description)
                    })

            saveButton.setOnClickListener {
                ideaViewModel.update(Idea(titleTextView.text.toString(),
                        descriptionTextView.text.toString(),
                        ideaViewModel.selectedStatus!!,
                        ideaViewModel.selectedCategory!!,
                        Calendar.getInstance().time,
                        extraIdeaId
                ))
                finish()
            }

        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

            saveButton.setOnClickListener {
                ideaViewModel.insert(Idea(titleTextView.text.toString(),
                        descriptionTextView.text.toString(),
                        ideaViewModel.selectedStatus!!,
                        ideaViewModel.selectedCategory!!
                ))
                finish()
            }
        }
    }

    private fun setupToolbar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_twotone_keyboard_arrow_left)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getStatusAndSetupView() {
        ideaViewModel.getAllStatus()?.observe(this,
                Observer<List<Status>> { status ->
                    val statusPositionOne = status?.get(0)
                    ideaViewModel.selectedStatus = statusPositionOne
                    statusTextView.text = statusPositionOne?.statusTitle
                    statusTextView.setBackgroundColor(resources.getColor(toButtonColor(statusPositionOne?.statusTitle)))

                })

        statusTextView.setOnClickListener {
            ideaViewModel.getAllStatus()?.observe(this,
                    Observer<List<Status>> { status ->
                        val currentStatusIndex = status?.indexOf(ideaViewModel.selectedStatus)
                        if (currentStatusIndex != -1) {
                            if (currentStatusIndex == status?.size?.minus(1)) {
                                ideaViewModel.selectedStatus = status?.get(0)
                            } else {
                                ideaViewModel.selectedStatus = status?.get(currentStatusIndex?.inc()!!)
                            }
                        }

                        statusTextView.text = ideaViewModel.selectedStatus?.statusTitle
                        statusTextView.setBackgroundColor(resources.getColor(toButtonColor(ideaViewModel.selectedStatus?.statusTitle)))

                    })
        }

    }

    private fun getCategoriesAndSetupView() {
        ideaViewModel.getAllCategories()?.observe(this,
                Observer<List<Category>> { categories ->
                    ideaViewModel.selectedCategory = categories?.get(0)
                    val selectedCategoryTile = ideaViewModel.selectedCategory?.categoryTitle
                    categoryNameTextView.text = selectedCategoryTile
                    categoryIcon.setImageResource(toDrawableId(selectedCategoryTile))
                })

        categoryNameTextView.setOnClickListener {
            showCategorySelectionDialog()
        }

        categoryIcon.setOnClickListener {
            showCategorySelectionDialog()
        }
    }

    private fun showCategorySelectionDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle(getString(R.string.category_dialog_title))

        // get categories and populate dialog
        ideaViewModel.getAllCategories()?.observe(this, Observer<List<Category>> {
            val categoriesList: Array<CharSequence>? = it?.map { category -> category.categoryTitle as CharSequence }?.toTypedArray()
            dialogBuilder.setItems(categoriesList) { _, which ->
                val newCategory = ideaViewModel.updateSelectedCategory(which)
                val categoryTitle = newCategory?.categoryTitle
                categoryNameTextView.text = categoryTitle
                categoryIcon.setImageResource(toDrawableId(categoryTitle))
            }
            dialogBuilder.show()
        })

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


    private fun updateStatusView(status: Status?) {
        statusTextView.text = status?.statusTitle
        statusTextView.setBackgroundColor(resources.getColor(toButtonColor(status?.statusTitle)))
    }

    private fun updateCategoryView(category: Category?) {
        categoryNameTextView.text = category?.categoryTitle
        categoryIcon.setImageResource(toDrawableId(category?.categoryTitle))
    }


}
