package com.example.jobstatustracker.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jobstatustracker.R
import com.example.jobstatustracker.data.PositionType
import com.example.jobstatustracker.data.SalaryType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddApplicationScreen(
    viewModel: MainViewModel,
    onCancel: () -> Unit,
    onDone: () -> Unit
) {

    var companyName by remember { mutableStateOf("") }
    var positionName by remember { mutableStateOf("") }

    var salaryType by remember { mutableStateOf(SalaryType.YEARLY) }
    var minValue by remember { mutableStateOf("0") }
    var maxValue by remember { mutableStateOf("0") }

    var positionType by remember { mutableStateOf(PositionType.INPERSON) }

    var error by remember { mutableStateOf<String?>(null) }

    BackHandler(onBack = onCancel)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_application)) },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                Spacer(Modifier.weight(1f))

                TextButton(onClick = onCancel) {
                    Text(stringResource(R.string.cancel))
                }

                Button(
                    onClick = {
                        viewModel.addJobApplication(
                            companyName,
                            positionName,
                            minValue.toInt(),
                            maxValue.toInt(),
                            salaryType,
                            positionType,
                        ) { result ->
                            result.onSuccess {
                                onDone() }
                            result.onFailure { error = it.message }
                        }
                    },
                    enabled = companyName.isNotBlank() && positionName.isNotBlank() && minValue.toInt() <= maxValue.toInt()
                ) {
                    Text(stringResource(R.string.create))
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = companyName,
                onValueChange = { companyName = it },
                label = { Text(stringResource(R.string.company_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = positionName,
                onValueChange = { positionName = it },
                label = { Text(stringResource(R.string.position_name)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text(
                text = stringResource(R.string.type),
                style = MaterialTheme.typography.titleMedium
            )
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                PositionType.entries.forEachIndexed { index, p ->
                    SegmentedButton(
                        selected = positionType == p,
                        onClick = { positionType = p },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = PositionType.entries.size
                        ),
                    ) {
                        Text(p.displayName)
                    }
                }
            }

            Text(
                text = stringResource(R.string.salary_type),
                style = MaterialTheme.typography.titleMedium
            )
            SingleChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                SalaryType.entries.forEachIndexed { index, s ->
                    SegmentedButton(
                        selected = salaryType == s,
                        onClick = { salaryType = s },
                        shape = SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = SalaryType.entries.size
                        ),
                    ) {
                        Text(s.displayName)
                    }
                }
            }


            Text(
                text = stringResource(R.string.range),
                style = MaterialTheme.typography.titleMedium
            )
            OutlinedTextField(
                value = minValue,
                onValueChange = { minValue = it },
                label = { Text(stringResource(R.string.min_value)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = maxValue,
                onValueChange = { maxValue = it },
                label = { Text(stringResource(R.string.max_value)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}