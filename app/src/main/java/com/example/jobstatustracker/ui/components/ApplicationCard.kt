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

    fun salarySpecified(value: Int?): Boolean {
        return value != null && value > 0
    }

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

            if (application.salaryType != null) {
                if (salarySpecified(application.minSalaryValue) && !salarySpecified(application.maxSalaryValue)) {
                    Text(
                        stringResource(
                            R.string.format_salary_int,
                            application.salaryType.displayName,
                            application.minSalaryValue!!,
                        )
                    )
                } else if (salarySpecified(application.maxSalaryValue) && (!salarySpecified(application.minSalaryValue) ||
                    application.minSalaryValue == application.maxSalaryValue)) {
                    Text(
                        stringResource(
                            R.string.format_salary_int,
                            application.salaryType.displayName,
                            application.maxSalaryValue!!,
                        )
                    )
                } else if (salarySpecified(application.minSalaryValue)  && salarySpecified(application.maxSalaryValue) ) {
                    Text(
                        stringResource(
                            R.string.format_salary_range_int,
                            application.salaryType.displayName,
                            application.minSalaryValue!!,
                            application.maxSalaryValue!!
                        )
                    )
                }
            }

            if (application.positionType != null) {
                Text(application.positionType.displayName)
            }
        }
    }



}