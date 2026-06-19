package com.example.jobstatustracker.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jobstatustracker.R
import com.example.jobstatustracker.data.JobApplication
import com.example.jobstatustracker.data.JobApplicationEntry
import com.example.jobstatustracker.ui.MainViewModel

@Composable
fun ApplicationList(
    viewModel: MainViewModel,
    selectedApplicationId: Long?,
    applications: List<JobApplication>,
    entries: List<JobApplicationEntry>,
    onSelectEntry: (JobApplication?) -> Unit
) {
    val application = applications.firstOrNull { it.id == selectedApplicationId }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(
            items = applications,
            key = {it.id}
        ) { application ->
            SwipeReveal(
                actionsWidth = 160f,
                actions = { progress ->
                    val scale = 0.8f + (0.2f * progress)

                    IconButton(
                        onClick = { viewModel.deleteJobApplication(application) },
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
                ApplicationCard(application, onSelectEntry)
            }
        }
    }
}