package com.example.mvvmdemo.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.data.repositories.IQuotesRepository
import com.example.mvvmdemo.data.repositories.QuotesRepository
import com.example.mvvmdemo.util.lazyDeferred
import kotlinx.coroutines.DelicateCoroutinesApi

class QuotesViewModel(
    quotesRepository: IQuotesRepository
) : ViewModel() {
    @DelicateCoroutinesApi
    val quotes by lazyDeferred {quotesRepository.getQuotes()}
}