package com.tulsivanol.android.quotes.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "text") val text: String = "",
    @ColumnInfo(name = "author") var author: String = "",
    @ColumnInfo(name = "date") val date: String = "",
    @ColumnInfo(name = "stars") val stars: Int = 0
)


