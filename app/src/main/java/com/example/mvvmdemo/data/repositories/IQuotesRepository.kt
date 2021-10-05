package com.example.mvvmdemo.data.repositories

import androidx.lifecycle.LiveData
import com.example.mvvmdemo.data.db.entities.Quote
import java.util.*

interface IQuotesRepository {
    suspend fun getQuotes(): LiveData<List<Quote>>

    suspend fun fetchQuotes()
    fun isFetchNeeded(savedAt: Calendar?): Boolean
    fun saveQuotes(quotes: List<Quote>)
}