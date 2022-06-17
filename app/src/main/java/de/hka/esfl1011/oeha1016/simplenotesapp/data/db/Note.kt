package de.hka.esfl1011.oeha1016.simplenotesapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.OffsetDateTime

@Entity
data class Note (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val created_date_time: OffsetDateTime?,
    val title: String,
    val content: String
)
