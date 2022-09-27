package com.tulsivanol.android.quotes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tulsivanol.android.quotes.data.Quote
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Testing the CRUD operations for DAO operations
 */
@RunWith(AndroidJUnit4::class)
open class QuoteDaoTest : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertQuoteTest() = runBlocking {
        val quote =
            Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
        appDatabase.quotesDao().insertQuote(quote)
        appDatabase.quotesDao().getQuotes().take(1).collect {
            assertEquals(it.size, 1)
        }
    }

    @Test
    fun deleteQuoteTest() = runBlocking {
        val quote =
            Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
        appDatabase.quotesDao().insertQuote(quote)

        appDatabase.quotesDao().deleteQuote(quote)
        appDatabase.quotesDao().getQuotes().take(1).collect {
            assertEquals(it.size, 0)
        }
    }

    @Test
    fun updateQuoteTest() = runBlocking {
        var quote =
            Quote(id = 1, text = "Hello World", author = "Ray Wenderlich", date = "27/12/1998")
        appDatabase.quotesDao().insertQuote(quote)
        quote.author = "Enzo Lizama"
        appDatabase.quotesDao().updateQuote(quote)
        assertEquals(
            appDatabase.quotesDao().getQuotes().first().first().author, "Enzo " +
                    "Lizama"
        )
    }

}