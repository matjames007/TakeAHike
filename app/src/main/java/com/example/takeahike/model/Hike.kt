package com.example.takeahike.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "hikes")
data class Hike(@PrimaryKey val id: UUID =UUID.randomUUID(), val category: String, val name: String,
                val distance: Double, val difficulty: String,
                val location: String, val description: String)
