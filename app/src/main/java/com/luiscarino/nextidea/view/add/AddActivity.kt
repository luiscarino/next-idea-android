package com.luiscarino.nextidea.view.add

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.lifecycle.Observer
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.model.room.entity.Category
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.model.room.entity.Status
import com.luiscarino.nextidea.util.toButtonColor
import com.luiscarino.nextidea.util.toDrawableId
import com.luiscarino.nextidea.viewmodel.IdeaViewModel
import kotlinx.android.synthetic.main.activity_add.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

/**
 * Activity that handles Ideas add and edit mode.
 */
class AddActivity : AppCompatActivity() {

    companion object {
        const val NO_ID_PROVIDED = -1L
        const val INTENT_ARG_ID = "ARG_IDEA_ID"
        const val DELETE_RESULT_CODE = 100
        const val DEFAULT_DELAY_MS = 500L

        fun getIntentInEditMode(ideaId: Long?, packageContext: Context): Intent {
            val intent = Intent(packageContext, AddActivity::class.java)
            intent.putExtra(INTENT_ARG_ID, ideaId)
            return intent
        }

        fun getIntentAddMode(packageContext: Context): Intent {
            return Intent(packageContext, AddActivity::class.java)
        }
    }

    private val ideaViewModel by viewModel<IdeaViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        val extraIdeaId = intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED)
        ideaViewModel.isEditMode = extraIdeaId != NO_ID_PROVIDED
        if (ideaViewModel.isEditMode.not()) {
            titleTextView.postDelayed(
                    {
                        val systemService = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        systemService.showSoftInput(titleTextView, InputMethodManager.SHOW_IMPLICIT)
                    },
                    DEFAULT_DELAY_MS)
        } else {
            ideaViewModel.getIdeaById(intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED))
                    .observe(this, Observer {
                        ideaViewModel.detailIdea = it
                        ideaViewModel.selectedStatus = it?.status
                        ideaViewModel.selectedCategory = it?.category
                        updateCategoryView(ideaViewModel.selectedCategory)
                        updateStatusView(ideaViewModel.selectedStatus)
                        titleTextView.setText(it?.title)
                        descriptionTextView.setText(it?.description)
                    })
        }

        setupToolbar()
        getCategoriesAndSetupView()
        getStatusAndSetupView()
    }

    private fun setupToolbar() {
        toolbar.title = ""
        setSupportActionBar(toolbar)

        val iconId = if (ideaViewModel.isEditMode) {
            R.drawable.ic_twotone_close
        } else {
            R.drawable.ic_twotone_keyboard_arrow_left
        }
        supportActionBar?.setHomeAsUpIndicator(iconId)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getStatusAndSetupView() {
        ideaViewModel.status.observe(this,
                Observer<List<Status>> { status ->
                    if (status?.isEmpty()?.not() == true && ideaViewModel.isEditMode.not()) {
                        val statusPositionOne = status[0]
                        ideaViewModel.selectedStatus = statusPositionOne
                        statusTextView.text = statusPositionOne.statusTitle
                        statusTextView.setBackgroundColor(resources.getColor(toButtonColor(statusPositionOne.statusTitle)))
                    }
                })

        statusTextView.setOnClickListener {
            ideaViewModel.status.observe(this,
                    Observer<List<Status>> { status ->
                        if (status?.isEmpty()?.not() == true) {
                            val currentStatusIndex = status.indexOf(ideaViewModel.selectedStatus)
                            if (currentStatusIndex != -1) {
                                if (currentStatusIndex == status.size.minus(1)) {
                                    ideaViewModel.selectedStatus = status[0]
                                } else {
                                    ideaViewModel.selectedStatus = status[currentStatusIndex.inc()]
                                }
                            } else {
                                val statusPositionOne = status[0]
                                ideaViewModel.selectedStatus = statusPositionOne
                                statusTextView.text = statusPositionOne.statusTitle
                                statusTextView.setBackgroundColor(resources.getColor(toButtonColor(statusPositionOne.statusTitle)))
                            }
                            statusTextView.text = ideaViewModel.selectedStatus?.statusTitle
                            statusTextView.setBackgroundColor(resources.getColor(toButtonColor(ideaViewModel.selectedStatus?.statusTitle)))
                        }
                    })
        }

    }

    private fun getCategoriesAndSetupView() {
        ideaViewModel.categories.observe(this,
                Observer<List<Category>> { categories ->
                    if (categories != null && categories.isEmpty().not() && ideaViewModel.isEditMode.not()) {
                        ideaViewModel.selectedCategory = categories[0]
                        val selectedCategoryTile = ideaViewModel.selectedCategory?.categoryTitle
                        categoryNameTextView.text = selectedCategoryTile
                        categoryIcon.setImageResource(toDrawableId(selectedCategoryTile))
                    }
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
        ideaViewModel.categories.observe(this, Observer<List<Category>> {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        if (intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED) == NO_ID_PROVIDED) {
            menu?.findItem(R.id.action_share)?.isVisible = false
            menu?.findItem(R.id.action_delete)?.isVisible = false
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // Respond to the action bar's Up/Home button
                NavUtils.navigateUpFromSameTask(this)
                finishWithAnimation()
                return true
            }
            R.id.action_save -> {
                saveOrUpdate()
                return true
            }
            R.id.action_share -> {
                shareIdea()
                return true
            }
            R.id.action_delete -> {
                val builder = AlertDialog.Builder(this, R.style.ConfirmationDialog)
                builder.setMessage(getString(R.string.alert_delete_title))
                        .setPositiveButton(getString(R.string.alert_delete_title_button_yes)) { _, _ -> deleteIdea() }
                        .setNegativeButton(getString(R.string.alert_delete_title_button_no)) { d, _ -> d.dismiss() }
                        .show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.activity_slide_down)
    }

    private fun updateStatusView(status: Status?) {
        statusTextView.text = status?.statusTitle
        statusTextView.setBackgroundColor(resources.getColor(toButtonColor(status?.statusTitle)))
    }

    private fun updateCategoryView(category: Category?) {
        categoryNameTextView.text = category?.categoryTitle
        categoryIcon.setImageResource(toDrawableId(category?.categoryTitle))
    }

    private fun shareIdea() {
        if (ideaViewModel.isEditMode.not()) return

        val formatTemplate = "%s\n%s\n%s\n%s"
        ideaViewModel.getIdeaById(intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED))
                .observe(this, Observer {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, formatTemplate.format(it?.title,
                                it?.description, it?.status?.statusTitle, it?.category?.categoryTitle))
                        type = "text/plain"
                    }
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.share_your_idea)))
                })
    }

    private fun saveOrUpdate() {
        if (ideaViewModel.isEditMode) {
            ideaViewModel.update(Idea(titleTextView.text.toString(),
                    descriptionTextView.text.toString(),
                    ideaViewModel.selectedStatus!!,
                    ideaViewModel.selectedCategory!!,
                    Calendar.getInstance().time,
                    intent.getLongExtra(INTENT_ARG_ID, NO_ID_PROVIDED)
            ))
        } else {
            ideaViewModel.insert(Idea(titleTextView.text.toString(),
                    descriptionTextView.text.toString(),
                    ideaViewModel.selectedStatus!!,
                    ideaViewModel.selectedCategory!!
            ))
        }
        finishWithAnimation()
    }

    private fun deleteIdea() {
        if (ideaViewModel.isEditMode.not()) return

        setResult(DELETE_RESULT_CODE, Intent().apply { putExtra(INTENT_ARG_ID, ideaViewModel.detailIdea?.id) })
        finishWithAnimation()

    }

    private fun finishWithAnimation() {
        finish()
        overridePendingTransition(0, R.anim.activity_slide_down)
    }

}
