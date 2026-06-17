package com.example.jobstatustracker.repository

import com.example.jobstatustracker.data.ActivityType
import com.example.jobstatustracker.data.JobApplication
import com.example.jobstatustracker.data.JobApplicationDao
import com.example.jobstatustracker.data.JobApplicationEntry
import com.example.jobstatustracker.data.PositionType
import com.example.jobstatustracker.data.SalaryType


class JobApplicationRepository(
    private val dao: JobApplicationDao
) {

    val applications = dao.getJobApplications()
    val entries = dao.getEntries()

    // =====================================================
    // Applications
    // =====================================================

    suspend fun addJobApplication(
        companyName: String,
        positionName: String,
        min: Int,
        max: Int,
        salaryType: SalaryType?,
        positionType: PositionType?
    ): Result<Unit> {

        if (companyName.isBlank()) {
            return Result.failure(Exception("Company Name is required"))
        }

        if (positionName.isBlank()) {
            return Result.failure(Exception("Position Name is required"))
        }

        if (min > max) {
            return Result.failure(Exception("Minimum salary must be lower than maximum salary"))
        }

        dao.insertJobApplication(
            JobApplication(
                companyName = companyName.trim(),
                positionName = positionName.trim(),
                minSalaryValue = min,
                maxSalaryValue = max,
                salaryType = salaryType,
                positionType = positionType
            )
        )

        return Result.success(Unit)
    }

    suspend fun deleteJobApplication(jobApplication: JobApplication) {
        dao.deleteJobApplication(jobApplication)
    }

    // =====================================================
    // Entries
    // =====================================================

    suspend fun saveEntryForJobApplication(
        applicationId: Long,
        date: String,
        description: String,
        activityType: ActivityType
    ) : Result<Unit> {
        val jobApplication = dao.getJobApplicationById(applicationId) ?: return Result.failure(
            Exception("Application ID doesnt match any job application")
        )

        dao.insertApplicationEntry(
            JobApplicationEntry(
                applicationId = jobApplication.id,
                date = date,
                description = description,
                activityType = activityType
            )
        )

        return Result.success(Unit)
    }

    suspend fun deleteJobApplicationEntry(entry: JobApplicationEntry) {
        dao.deleteJobApplicationEntry(entry)
    }

    suspend fun getEntriesByType(activityType: ActivityType): List<JobApplicationEntry> {
        return dao.getEntriesForType(activityType)
    }

    suspend fun getEntriesByApplication(id: Long): List<JobApplicationEntry> {
        return dao.getEntriesForApplication(id)
    }

    suspend fun getEntriesByCompany(company: String): List<JobApplicationEntry> {
        return dao.getEntriesForCompany(company)
    }
}