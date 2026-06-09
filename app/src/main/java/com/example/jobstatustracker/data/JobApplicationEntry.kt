package com.example.jobstatustracker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "entries",
    foreignKeys = [
        ForeignKey(
            entity = JobApplication::class,
            parentColumns = ["id"],
            childColumns = ["applicationId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class JobApplicationEntry(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val applicationId: Long,

    val date: String,

    val description: String,
    val activityType: ActivityType,
)