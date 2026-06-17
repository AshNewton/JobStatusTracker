package com.example.jobstatustracker.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jobstatustracker.R
import com.example.jobstatustracker.data.JobApplication
import com.example.jobstatustracker.data.JobApplicationEntry
import com.example.jobstatustracker.ui.components.EntryCard
import com.example.jobstatustracker.ui.components.SwipeReveal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationDetailsScreen(
    application: JobApplication?,
    viewModel: MainViewModel,
    onBack: () -> Unit,
    onAddEntry: () -> Unit,
) {

    val entries by viewModel.entries.collectAsState()
    LaunchedEffect(application?.id) {
        application?.let {
            viewModel.setApplicationId(it.id)
        }
    }


    BackHandler(onBack = onBack)

    val application = application ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.application_details)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(application.companyName, style = MaterialTheme.typography.headlineLarge)

                Spacer(Modifier.height(8.dp))

                Text(application.positionName, style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(8.dp))

                if (application.minSalaryValue != null && application.maxSalaryValue == null) {
                    Text(application.minSalaryValue.toString(), style = MaterialTheme.typography.titleSmall)
                } else if (application.minSalaryValue == null && application.maxSalaryValue != null) {
                    Text(application.maxSalaryValue.toString(), style = MaterialTheme.typography.titleSmall)
                } else if (application.minSalaryValue == application.maxSalaryValue) {
                    Text(application.minSalaryValue.toString(), style = MaterialTheme.typography.titleSmall)
                } else if (application.minSalaryValue != null && application.maxSalaryValue != null
                    && application.salaryType != null) {
                    Text(
                        stringResource(
                            R.string.format_salary_int,
                            application.salaryType.displayName,
                            application.minSalaryValue,
                            application.maxSalaryValue
                        ), style = MaterialTheme.typography.titleSmall
                    )
                }

                Spacer(Modifier.height(8.dp))

                Button(
                    onClick = onAddEntry,
                ) {
                    Text(stringResource(R.string.add_entry))
                }
            }

            items(
                items = entries,
                key = {it.id}
            ) { entry ->
                SwipeReveal(
                    actionsWidth = 160f,
                    actions = { progress ->
                        val scale = 0.8f + (0.2f * progress)

                        IconButton(
                            onClick = { viewModel.deleteJobApplicationEntry(entry) },
                            modifier = Modifier.graphicsLayer {
                                this.alpha = progress
                                this.scaleX = scale
                                this.scaleY = scale
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                ) {
                    EntryCard(entry)
                }
            }
        }
    }
}