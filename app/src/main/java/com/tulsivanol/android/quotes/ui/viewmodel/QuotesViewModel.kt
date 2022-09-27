package com.tulsivanol.android.quotes.ui.viewmodel

import androidx.lifecycle.*
import com.tulsivanol.android.quotes.data.Quote
import com.tulsivanol.android.quotes.data.QuotesRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class QuotesViewModel(private val repository: QuotesRepository) : ViewModel() {

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    fun insertQuote(quote: Quote) {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            repository.insert(quote)
        }
        _dataLoading.postValue(false)
    }

    fun updateQuote(quote: Quote) {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            repository.update(quote)
        }
        _dataLoading.postValue(false)
    }

    fun delete(quote: Quote) {
        _dataLoading.postValue(true)
        viewModelScope.launch {
            repository.delete(quote)
        }
        _dataLoading.postValue(false)
    }

    fun getAllQuotes(): LiveData<List<Quote>> = liveData {
        _dataLoading.postValue(true)
        repository.getQuotes().collect { quotes ->
            _dataLoading.postValue(false)
            emit(quotes)
        }
    }

}