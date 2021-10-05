package com.example.mvvmdemo.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.data.db.AppDatabase
import com.example.mvvmdemo.data.db.QuotesDao
import com.example.mvvmdemo.data.db.entities.Quote
import com.example.mvvmdemo.data.network.MyApi
import com.example.mvvmdemo.data.network.SafeApiRequest
import com.example.mvvmdemo.data.preferences.PreferenceProvider
import com.example.mvvmdemo.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

const val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val quotesDao: QuotesDao,
    private val sharedPreferences: PreferenceProvider
): SafeApiRequest(), IQuotesRepository {
    private val quotes = MutableLiveData<List<Quote>>()

    init{

        quotes.observeForever{
            saveQuotes(it)
        }
    }

    override suspend fun getQuotes(): LiveData<List<Quote>> {

        return withContext(Dispatchers.IO){
            fetchQuotes()
            quotesDao.getQuotes()
        }
    }

    override suspend fun fetchQuotes() {

        val lastSavedAt = sharedPreferences.getLastSavedAt()
        if(lastSavedAt == null || isFetchNeeded(lastSavedAt.stringToCalendar())){

            val response = apiRequest { api.getQuotes() }
            quotes.postValue(response.quotes)
        }
    }

    override fun isFetchNeeded(savedAt: Calendar?): Boolean {
        return savedAt?.get(Calendar.HOUR)?.minus(Calendar.getInstance().get(Calendar.HOUR))!! > MINIMUM_INTERVAL
    }

    override fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            val currentTime = Calendar.getInstance().time

            sharedPreferences.saveLastSavedAt(currentTime.dateToString())
            quotesDao.saveAllQuotes(quotes)
        }
    }
}