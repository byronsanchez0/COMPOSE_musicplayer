package com.example.musicplayer.modules

import com.example.musicplayer.ui.listscreen.ListViewModel
import com.example.musicplayer.models.SongRepository
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single {
        androidContext().getSharedPreferences("sh", 0)
    }
    single {
        Gson()
    }
    single {
        SongRepository(androidContext(), get(), get())
    }

    // ViewModels
    viewModel {
        ListViewModel(get())
    }
    viewModelOf(::ListViewModel)

}