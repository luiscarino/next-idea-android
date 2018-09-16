package com.luiscarino.nextidea.view.list.view

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.model.room.entity.Idea
import com.luiscarino.nextidea.ratemyapp.RateMyApp
import com.luiscarino.nextidea.util.formatToMonthDayYear
import com.luiscarino.nextidea.view.add.AddActivity
import com.luiscarino.nextidea.view.list.view.adapter.IdeaListItemDelegateAdapter
import com.luiscarino.nextidea.view.list.view.adapter.IdeaListItemRecyclerRecyclerViewType
import com.luiscarino.nextidea.view.list.view.adapter.IdeaListRecyclerViewAdapter
import com.luiscarino.nextidea.viewmodel.IdeaViewModel
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.android.architecture.ext.viewModel

/**
 * Activity that displays a list of Ideas.
 */
class ListActivity : AppCompatActivity(), IdeaListItemDelegateAdapter.Actions {

    private val ideaViewModel: IdeaViewModel by viewModel()
    private val startEditActivityCode = 1000
    private lateinit var ideasAdapter: IdeaListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            startActivity(AddActivity.getIntentAddMode(this))
        }

        ideasAdapter = IdeaListRecyclerViewAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.adapter = ideasAdapter


        ideaViewModel.getAllIdeas()?.observe(this, fetchIdeasObserver)
        RateMyApp(this, RateMyApp.RateMyAppConfig(5)).init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCardClicked(id: Long?) {
        startActivityForResult(
                AddActivity.getIntentInEditMode(id, this),
                startEditActivityCode)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (startEditActivityCode == requestCode && resultCode == AddActivity.DELETE_RESULT_CODE) {
            ideaViewModel.delete(data?.getLongExtra(AddActivity.INTENT_ARG_ID, -1)!!)
                    ?.observe(this, fetchIdeasObserver)
        }
    }

    private val fetchIdeasObserver = Observer<List<Idea>> { ideaList ->
        if (ideaList!!.isEmpty().not()) {
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
