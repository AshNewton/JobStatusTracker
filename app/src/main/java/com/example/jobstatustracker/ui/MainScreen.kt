package com.example.jobstatustracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.jobstatustracker.data.*

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val jobApplications by viewModel.jobApplications.collectAsState()
    val entries by viewModel.entries.collectAsState()

    var screen by remember { mutableStateOf(Screen.LIST) }
    var selectedApplication by remember { mutableStateOf<JobApplication?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ){
        when (screen) {
            Screen.LIST -> ListApplicationsScreen(
                viewModel = viewModel,
                applications = jobApplications,
                entries = entries,
                onAddApplication = {
                    selectedApplication = null
                    screen = Screen.ADD_APPLICATION
                },
                onDeleteApplication = {
                    selectedApplication = null
                },
                onSelectEntry = {
                    selectedApplication = it
                    screen = Screen.APPLICATION_DETAILS
                }
            )
            Screen.APPLICATION_DETAILS -> ApplicationDetailsScreen(
                application = selectedApplication,
                viewModel = viewModel,
                onBack = { screen = Screen.LIST },
                onAddEntry = {screen = Screen.ADD_ENTRY_FOR_APPLICATION}
            )
            Screen.ADD_APPLICATION -> AddApplicationScreen(
                viewModel = viewModel,
                onCancel = { screen = Screen.LIST },
                onDone = { screen = Screen.LIST }
            )
            Screen.ADD_ENTRY_FOR_APPLICATION -> AddEntryScreen(
                application = selectedApplication,
                viewModel = viewModel,
                onCancel = { screen = Screen.APPLICATION_DETAILS },
                onDone = { screen = Screen.APPLICATION_DETAILS }
            )
        }}


}
