package com.luiscarino.nextidea.di

import com.luiscarino.nextidea.model.room.repository.IdeaRepository
import com.luiscarino.nextidea.viewmodel.IdeaViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

val appModule : Module = applicationContext{
    bean { IdeaRepository(get()) }
    viewModel { IdeaViewModel(get(), get()) }
}