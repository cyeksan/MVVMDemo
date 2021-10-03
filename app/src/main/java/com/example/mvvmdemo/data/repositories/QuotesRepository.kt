package com.example.mvvmdemo.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.entities.Quote
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.SafeApiRequest
import com.example.mvvmdemo.data.preferences.PreferenceProvider
import com.example.mvvmdemo.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

private const val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val sharedPreferences: PreferenceProvider
): SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    init{
        quotes.observeForever{
            saveQuotes(it)
        }
    }

    suspend fun getQuotes(): LiveData<List<Quote>> {

        return withContext(Dispatchers.IO){
            fetchQuotes()
            db.getQuotesDao().getQuotes()
        }
    }

    private suspend fun fetchQuotes() {

        val lastSavedAt = sharedPreferences.getLastSavedAt()
        if(lastSavedAt == null || isFetchNeeded(lastSavedAt.stringToCalendar())){

            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    private fun isFetchNeeded(savedAt: Calendar?): Boolean {
        return savedAt?.get(Calendar.HOUR)?.minus(Calendar.getInstance().get(Calendar.HOUR))!! > MINIMUM_INTERVAL
    }

    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            val currentTime = Calendar.getInstance().time

            sharedPreferences.saveLastSavedAt(currentTime.dateToString())
            db.getQuotesDao().saveAllQuotes(quotes)
        }
    }
}