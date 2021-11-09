package com.empire.habits.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table")
data class Word(@PrimaryKey @ColumnInfo(name = "word") val word: String,
                @ColumnInfo(name = "description") val description: String,
                @ColumnInfo(name = "hour") val hour: Long,
                @ColumnInfo(name = "minute") val minute: Long,
                @ColumnInfo(name = "color") val color: String,
                @ColumnInfo(name = "count") val count: Float,
                @ColumnInfo(name = "repeat") val repeat: Boolean)