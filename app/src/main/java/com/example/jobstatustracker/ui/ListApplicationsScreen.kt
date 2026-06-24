package com.example.jobstatustracker.ui

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jobstatustracker.ui.components.ApplicationList
import com.example.jobstatustracker.R
import com.example.jobstatustracker.data.JobApplication
import com.example.jobstatustracker.data.JobApplicationEntry
import java.util.Locale
import java.util.Locale.getDefault

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListApplicationsScreen(
    viewModel: MainViewModel,
    applications: List<JobApplication>,
    entries: List<JobApplicationEntry>,
    onAddApplication: () -> Unit,
    onSelectEntry: (JobApplication?) -> Unit
) {
    var selectedCompanyName by remember { mutableStateOf<String?>(null) }
    var dropdownExpanded by remember { mutableStateOf(false) }

    val activity = LocalActivity.current

    val companyNames = applications.map { it.companyName }.toSet().toList().sortedBy{it}

    BackHandler {
        activity?.moveTaskToBack(true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.applications)) },
                actions = {
                    IconButton(
                        onClick = onAddApplication
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ExposedDropdownMenuBox(
                        expanded = dropdownExpanded,
                        onExpandedChange = { dropdownExpanded = !dropdownExpanded },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        OutlinedTextField(
                            value = selectedCompanyName ?: "Select Company",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.menuAnchor().fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(dropdownExpanded)
                            }
                        )

                        ExposedDropdownMenu(
                            expanded = dropdownExpanded,
                            onDismissRequest = { dropdownExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.none)) },
                                onClick = {
                                    selectedCompanyName = null
                                    dropdownExpanded = false
                                }
                            )
                            companyNames.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        selectedCompanyName = it
                                        dropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    ApplicationList(
                        viewModel = viewModel,
                        applications = applications.filter {
                            selectedCompanyName == null || it.companyName.equals(selectedCompanyName, ignoreCase = true)
                        }.reversed(),
                        entries = entries,
                        onSelectEntry = onSelectEntry,
                    )
                }
            }
        }
    }
}