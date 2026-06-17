package com.example.jobstatustracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jobstatustracker.repository.JobApplicationRepository
import com.example.jobstatustracker.ui.MainScreen
import com.example.jobstatustracker.ui.MainViewModel
import com.example.jobstatustracker.data.AppDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = AppDatabase.getDatabase(applicationContext).dao()
        val trackerRepository = JobApplicationRepository(dao)

        setContent {
            val vm: MainViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return MainViewModel(trackerRepository) as T
                }
            })

            MainScreen(vm)
        }
    }
}