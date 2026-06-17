package com.example.jobstatustracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobstatustracker.data.ActivityType
import com.example.jobstatustracker.data.JobApplication
import com.example.jobstatustracker.data.JobApplicationEntry
import com.example.jobstatustracker.data.SalaryType
import com.example.jobstatustracker.repository.JobApplicationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class MainViewModel(private val applicationRepo: JobApplicationRepository) : ViewModel() {

    // =====================================================
    // Trackers
    // =====================================================

    val jobApplications = applicationRepo.applications.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addJobApplication(
        companyName: String,
        positionName: String,
        min: Int?,
        max: Int?,
        salaryType: SalaryType?,
        onResult: (Result<Unit>) -> Unit
    ) {
        viewModelScope.launch {
            val result = applicationRepo.addJobApplication(companyName, positionName, min, max, salaryType)
            onResult(result)
        }
    }

    fun deleteJobApplication(application: JobApplication) {
        viewModelScope.launch {
            applicationRepo.deleteJobApplication(application)
        }
    }

    // =====================================================
    // Entries
    // =====================================================

    val entries = applicationRepo.entries.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun addEntryForJobApplication(
        applicationId: Long,
        date: String,
        description: String,
        activityType: ActivityType,
        onResult: (Result<Unit>) -> Unit
    ) {
        viewModelScope.launch {
            val result = applicationRepo.saveEntryForJobApplication(applicationId, date, description, activityType)
            onResult(result)
        }
    }

    fun deleteJobApplicationEntry(entry: JobApplicationEntry) {
        viewModelScope.launch {
            applicationRepo.deleteJobApplicationEntry(entry)
        }
    }

}