package com.luiscarino.nextidea.view.detail

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.luiscarino.nextidea.R
import com.luiscarino.nextidea.viewmodel.IdeaViewModel
import org.koin.android.architecture.ext.viewModel

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