package com.example.mvvmdemo

import android.app.Application
import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.QuotesDao
import com.example.mvvmdemo.data.db.UserDao
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.NetworkConnectionInterceptor
import com.example.mvvmdemo.data.preferences.PreferenceProvider
import com.example.mvvmdemo.data.repositories.QuotesRepository
import com.example.mvvmdemo.data.repositories.UserRepository
import com.example.mvvmdemo.ui.auth.AuthViewModelFactory
import com.example.mvvmdemo.ui.home.profile.ProfileViewModelFactory
import com.example.mvvmdemo.ui.home.quotes.QuotesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@App))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }
        bind<UserDao>() with singleton { instance<AppDatabase>().getUserDao() }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind<QuotesDao>() with singleton { instance<AppDatabase>().getQuotesDao() }
        bind() from singleton { QuotesRepository(instance(), instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { QuotesViewModelFactory(instance()) }

    }
}