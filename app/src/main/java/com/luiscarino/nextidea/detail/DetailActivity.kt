package com.luiscarino.nextidea.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.list.viewmodel.IdeaViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {


    private val ideaViewModel: IdeaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)

        ideaViewModel.get(intent.getLongExtra("idea.id", -1))
                ?.observe(this, Observer {
                    Toast.makeText(applicationContext, it?.title, Toast.LENGTH_LONG).show()
                })

    }
}