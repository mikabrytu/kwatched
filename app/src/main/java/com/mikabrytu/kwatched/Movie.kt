package com.mikabrytu.kwatched

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import kotlin.time.Duration

@Entity
data class Movie(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String? = "",
    @ColumnInfo(name = "poster_url") val posterUrl: String? = "",
    @ColumnInfo(name = "synopsis") val synopsis: String? = "",
    @ColumnInfo(name = "director") val director: String? = "",
    @ColumnInfo(name = "user_notes") val userNotes: String? = "",
    @ColumnInfo(name = "duration") val duration: Int? = 0,
    @ColumnInfo(name = "released_date") val releasedDate: String? = "", // TODO: Make this a Date and convert to database
    @ColumnInfo(name = "watched_date") val watchedDate: String? = "", // TODO: Make this a Date and convert to database
)
