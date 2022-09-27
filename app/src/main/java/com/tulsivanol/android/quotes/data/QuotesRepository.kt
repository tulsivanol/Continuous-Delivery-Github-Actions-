package com.tulsivanol.android.quotes.data

import kotlinx.coroutines.flow.Flow

interface QuotesRepository {

    fun insert(quote: Quote)

    fun update(quote: Quote)

    fun delete(quote: Quote)

    fun getQuotes(): Flow<List<Quote>>
}