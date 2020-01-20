package com.luiscarino.nextidea.view.list.view

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.model.room.NextIdeaDatabase
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.ratemyapp.PreferencesContract
import com.luiscarino.nextidea.ratemyapp.RateMyApp
import com.luiscarino.nextidea.util.formatToMonthDayYear
import com.luiscarino.nextidea.util.populateDatabase
import com.luiscarino.nextidea.view.add.AddActivity
import com.luiscarino.nextidea.view.list.view.adapter.IdeaListItemDelegateAdapter
import com.luiscarino.nextidea.view.list.view.adapter.IdeaListItemRecyclerRecyclerViewType
import com.luiscarino.nextidea.view.list.view.adapter.IdeaListRecyclerViewAdapter
import com.luiscarino.nextidea.viewmodel.IdeaViewModel
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Activity that displays a list of Ideas.
 */
class ListActivity : AppCompatActivity(), IdeaListItemDelegateAdapter.Actions {

    private val ideaViewModel: IdeaViewModel by viewModel()
    private val startEditActivityCode = 1000
    private lateinit var ideasAdapter: IdeaListRecyclerViewAdapter
    private val database: NextIdeaDatabase by inject()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onStart() {
        super.onStart()
        overridePendingTransition(R.anim.activity_hold, R.anim.activity_hold)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createDatabase()
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            startActivity(AddActivity.getIntentAddMode(this))
        }

        ideasAdapter = IdeaListRecyclerViewAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.adapter = ideasAdapter

        ideaViewModel.ideas.observe(this, fetchIdeasObserver)

        RateMyApp(this, RateMyApp.RateMyAppConfig(
                2,
                5,
                false))
                .init()
    }

    private fun createDatabase() {
        if (sharedPreferences.getBoolean(PreferencesContract.PREF_IS_FIRST_LAUNCH, true)) {
            lifecycleScope.launch {
                populateDatabase(database)
                sharedPreferences.edit().putBoolean(PreferencesContract.PREF_IS_FIRST_LAUNCH, false).apply()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onCardClicked(id: Long?) {
        startActivityForResult(
                AddActivity.getIntentInEditMode(id, this),
                startEditActivityCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (startEditActivityCode == requestCode && resultCode == AddActivity.DELETE_RESULT_CODE) {
            ideaViewModel.delete(data?.getLongExtra(AddActivity.INTENT_ARG_ID, -1) ?: -1)
                    ?.observe(this, fetchIdeasObserver)
        }
    }

    private val fetchIdeasObserver = Observer<List<Idea>> { ideaList ->
        if (ideaList?.isEmpty()?.not() == true) {
            val ideas = ideaList.map { idea: Idea ->
                IdeaListItemRecyclerRecyclerViewType(idea.id,
                        idea.title,
                        idea.description,
                        idea.category.categoryId,
                        idea.category.categoryTitle,
                        idea.lastUpdatedDate.formatToMonthDayYear(),
                        idea.status.statusId,
                        idea.status.statusTitle)
            }
            ideasAdapter.setIdeas(ideas)
            recyclerview.visibility = View.VISIBLE
            emptyListContainer.visibility = View.GONE
        } else {
            recyclerview.visibility = View.GONE
            emptyListContainer.visibility = View.VISIBLE
        }
    }

}
