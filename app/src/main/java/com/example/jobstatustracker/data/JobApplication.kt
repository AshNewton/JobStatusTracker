package com.example.jobstatustracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "applications")
data class JobApplication(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    val companyName: String,
    val positionName: String,

    val minSalaryValue: Int? = null,
    val maxSalaryValue: Int? = null,
    val salaryType: SalaryType? = null,

    val positionType: PositionType? = null
)