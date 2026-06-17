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
    var selectedEntry by remember { mutableStateOf<JobApplication?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ){
        when (screen) {
            Screen.LIST -> ListApplications(
                viewModel = viewModel,
                applications = jobApplications,
                entries = entries,
                onAddApplication = {
                    selectedEntry = null
                    screen = Screen.ADD_APPLICATION
                },
                onDeleteApplication = {
                    selectedEntry = null
                },
                onSelectEntry = {
                    selectedEntry = it
                    screen = Screen.APPLICATION_DETAILS
                }
            )

            Screen.APPLICATION_DETAILS -> TODO()
            Screen.ADD_APPLICATION -> TODO()
            Screen.ADD_ENTRY_FOR_APPLICATION -> TODO()
        }}


}
