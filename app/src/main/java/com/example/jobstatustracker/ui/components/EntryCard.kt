package com.example.jobstatustracker.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobstatustracker.data.JobApplicationEntry

@Composable
fun EntryCard(
    entry: JobApplicationEntry,
    ) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(entry.activityType.displayName, style = MaterialTheme.typography.titleLarge)
            Text(entry.date, style = MaterialTheme.typography.titleSmall)

            if (entry.description.isNotEmpty()) {
                Text(entry.description)
            }
        }
    }
}