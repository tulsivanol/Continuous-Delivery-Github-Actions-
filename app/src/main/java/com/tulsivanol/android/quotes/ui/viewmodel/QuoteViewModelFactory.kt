@file:Suppress("UNCHECKED_CAST")

package com.tulsivanol.android.quotes.ui.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tulsivanol.android.quotes.data.QuotesRepository

@SuppressLint("UNCHECKED_CAST")
class QuoteViewModelFactory(
    private val repository: QuotesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuotesViewModel(repository) as T
    }
}