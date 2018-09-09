package com.luiscarino.nextidea.list.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.add.AddActivity
import com.luiscarino.nextidea.list.model.room.entity.Idea
import com.luiscarino.nextidea.list.view.adapter.IdeaListItemDelegateAdapter
import com.luiscarino.nextidea.list.view.adapter.IdeaListItemRecyclerRecyclerViewType
import com.luiscarino.nextidea.list.view.adapter.IdeaListRecyclerViewAdapter
import com.luiscarino.nextidea.list.viewmodel.IdeaViewModel
import com.luiscarino.nextidea.util.formatToMonthDayYear
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.android.architecture.ext.viewModel

/**
 * Activity that displays a list of Ideas.
 */
class ListActivity : AppCompatActivity(), IdeaListItemDelegateAdapter.Actions {

    private val ideaViewModel: IdeaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            startActivity(AddActivity.getIntentAddMode(this))
        }

        val ideasAdapter = IdeaListRecyclerViewAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.adapter = ideasAdapter

        ideaViewModel.getAllIdeas()?.observe(this,
                Observer<List<Idea>> { ideaList ->
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
                    }
                })

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

    override fun onCardClicked(id:Long?) {
        startActivity(AddActivity.getIntentInEditMode(id, this))
    }


}
