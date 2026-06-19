package com.example.jobstatustracker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jobstatustracker.R
import com.example.jobstatustracker.data.JobApplication

@Composable
fun ApplicationCard(
    application: JobApplication,
    onSelectEntry: (JobApplication?) -> Unit
    ) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth()
            .clickable {
                onSelectEntry(application)
            }
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(application.companyName, style = MaterialTheme.typography.titleLarge)
            Text(application.positionName, style = MaterialTheme.typography.titleSmall)

            if (application.minSalaryValue != null && application.maxSalaryValue == null) {
                Text(application.minSalaryValue.toString())
            } else if (application.minSalaryValue == null && application.maxSalaryValue != null) {
                Text(application.maxSalaryValue.toString())
            } else if (application.minSalaryValue == application.maxSalaryValue) {
                Text(application.minSalaryValue.toString())
            } else if (application.minSalaryValue != null && application.maxSalaryValue != null
                && application.salaryType != null) {
                Text(
                    stringResource(
                        R.string.format_salary_int,
                        application.salaryType.displayName,
                        application.minSalaryValue,
                        application.maxSalaryValue
                    )
                )
            }

            if (application.positionType != null) {
                Text(application.positionType.displayName)
            }
        }
    }



}