package com.tulsivanol.android.quotes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuotesDao {

    @Query("SELECT * FROM quotes ORDER BY id DESC")
    fun getQuotes(): Flow<List<Quote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuote(quote: Quote): Long

    @Update
    fun updateQuote(quote: Quote): Int

    @Delete
    fun deleteQuote(quote: Quote): Int
}