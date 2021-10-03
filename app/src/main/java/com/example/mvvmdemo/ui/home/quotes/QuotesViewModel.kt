package com.example.mvvmdemo.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.example.mvvmdemo.data.repositories.QuotesRepository
import com.example.mvvmdemo.util.lazyDeferred

class QuotesViewModel(
    quotesRepository: QuotesRepository
) : ViewModel() {
    val quotes by lazyDeferred {quotesRepository.getQuotes()}
}