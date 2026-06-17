package com.example.jobstatustracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface JobApplicationDao {

    // =====================================================
    // Applications
    // =====================================================

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJobApplication(tracker: JobApplication)

    @Query("SELECT * FROM applications WHERE id = :id LIMIT 1")
    fun getJobApplicationById(id: Long): JobApplication?

    @Query("SELECT * FROM applications")
    fun getJobApplications(): Flow<List<JobApplication>>

    @Query("SELECT * FROM applications WHERE LOWER(companyName) = LOWER(:name)")
    fun getJobApplicationsByCompanyName(name: String): Flow<List<JobApplication>>

    @Query("SELECT COUNT(*) FROM applications WHERE LOWER(companyName) = LOWER(:name)")
    suspend fun countTrackersByCompanyName(name: String): Int

    @Delete
    suspend fun deleteJobApplication(application: JobApplication)


    // =====================================================
    // Entries
    // =====================================================

    @Insert
    suspend fun insertApplicationEntry(entry: JobApplicationEntry): Long

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getEntries(): Flow<List<JobApplicationEntry>>

    @Query("SELECT * FROM entries WHERE activityType = :type")
    suspend fun getEntriesForType(type: ActivityType): List<JobApplicationEntry>

    @Query("""
    SELECT entries.* FROM entries
    INNER JOIN applications ON entries.applicationId = applications.id
    WHERE LOWER(applications.companyName) = LOWER(:companyName)
    """)
    suspend fun getEntriesForCompany(companyName: String): List<JobApplicationEntry>

    @Query("""
    SELECT e.* FROM entries e
    INNER JOIN entries a ON e.applicationId = a.id
    WHERE a.id = :id
    """)
    suspend fun getEntriesForApplication(id: Long): List<JobApplicationEntry>

    @Delete
    suspend fun deleteJobApplicationEntry(entry: JobApplicationEntry)
}