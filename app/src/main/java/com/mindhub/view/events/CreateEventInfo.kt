package com.mindhub.view.events

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mindhub.services.toBrazilianDateFormat
import com.mindhub.services.toTimeFormat
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.SpacedColumn
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun CreateEventInfo(
    navigator: DestinationsNavigator
) {
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            TopAppBar(
                title = { Text(text = "Adicionar um evento", style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                    }
                },
                actions = {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Continuar")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SpacedColumn(
                spacing = 8,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .weight(1.0f)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                OutlinedTextField(
                    value = "",
                    label = { Text(text = "Título") },
                    placeholder = { Text(text = "Digite o título") },
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = "",
                    label = { Text(text = "Descrição") },
                    placeholder = { Text(text = "Digite a descrição") },
                    onValueChange = {},
                    modifier = Modifier
                        .height(300.dp)
                        .fillMaxWidth()
                )

                var isDatePickerOpened by remember { mutableStateOf(false) }
                var selectedDate by remember { mutableStateOf("") }
                val datePickerState = rememberDatePickerState()

                if (isDatePickerOpened) {
                    DatePickerDialog(
                        onDismissRequest = { isDatePickerOpened = false },
                        confirmButton = {
                            Button(onClick = {
                                datePickerState.selectedDateMillis?.let {
                                    selectedDate = it.toBrazilianDateFormat()
                                }

                                isDatePickerOpened = false
                            }) {
                                Text(text = "Escolher data")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                val dateInteraction = remember { MutableInteractionSource() }.also {
                    if (it.collectIsFocusedAsState().value) {
                        isDatePickerOpened = true
                        focusManager.clearFocus(force = true)
                    }
                }

                OutlinedTextField(
                    value = selectedDate,
                    onValueChange = { },
                    label = { Text(text = "Data") },
                    placeholder = { Text(text = "Selecione a data") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    readOnly = true,
                    interactionSource = dateInteraction
                )

                var isTimePickerOpened by remember { mutableStateOf(false) }
                var selectedTime by remember { mutableStateOf("") }
                val timePickerState = rememberTimePickerState()

                if (isTimePickerOpened) {
                    Dialog(onDismissRequest = { isTimePickerOpened = false }) {
                        Surface(
                            modifier = Modifier
                                .wrapContentWidth()
                                .wrapContentHeight(),
                            shape = MaterialTheme.shapes.large,
                            tonalElevation = AlertDialogDefaults.TonalElevation
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                TimePicker(
                                    state = timePickerState,
                                    modifier = Modifier.padding(16.dp)
                                )
                                
                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    Button(onClick = {
                                        selectedTime = timePickerState.toTimeFormat()
                                        isTimePickerOpened = false
                                    }) {
                                        Text(text = "Escolher horário")
                                    }
                                }
                            }
                        }
                    }
                }

                val timeInteraction = remember { MutableInteractionSource() }.also {
                    if (it.collectIsFocusedAsState().value) {
                        isTimePickerOpened = true
                        focusManager.clearFocus(force = true)
                    }
                }

                OutlinedTextField(
                    value = selectedTime,
                    onValueChange = { },
                    label = { Text(text = "Horário") },
                    placeholder = { Text(text = "Selecione o horário") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    readOnly = true,
                    interactionSource = timeInteraction
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateEventInfoPreview() {
    MindHubTheme {
        CreateEventInfo(navigator = EmptyDestinationsNavigator)
    }
}