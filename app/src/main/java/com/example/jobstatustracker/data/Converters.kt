package com.example.jobstatustracker.data

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromSalaryType(value: SalaryType): String =
        value.name

    @TypeConverter
    fun toSalaryType(value: String): SalaryType =
        SalaryType.valueOf(value)

    @TypeConverter
    fun fromActivityType(type: ActivityType): String =
        type.name

    @TypeConverter
    fun toActivityType(value: String): ActivityType =
        ActivityType.valueOf(value)
}