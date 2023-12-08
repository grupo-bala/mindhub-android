package com.mindhub.view.composables.events

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
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
import androidx.compose.ui.window.DialogProperties
import com.mindhub.common.ext.toBrazilianDateFormat
import com.mindhub.common.ext.toTimeFormat
import com.mindhub.ui.theme.MindHubTheme
import com.mindhub.view.layouts.SpacedColumn
import com.mindhub.viewmodel.event.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFields(
    viewModel: EventViewModel
) {
    val focusManager = LocalFocusManager.current

    SpacedColumn(
        spacing = 8,
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        var isDatePickerOpened by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

        if (isDatePickerOpened) {
            DatePickerDialog(
                onDismissRequest = { isDatePickerOpened = false },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.let {
                            viewModel.date = it.toBrazilianDateFormat()
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
            value = viewModel.date,
            onValueChange = { },
            label = { Text(text = "Data*") },
            placeholder = { Text(text = "Selecione a data") },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            interactionSource = dateInteraction
        )

        var isTimePickerOpened by remember { mutableStateOf(false) }
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
                                viewModel.time = timePickerState.toTimeFormat()
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
            value = viewModel.time,
            onValueChange = { },
            label = { Text(text = "Horário*") },
            placeholder = { Text(text = "Selecione o horário") },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            interactionSource = timeInteraction
        )

        var isMapExpanded by remember {
            mutableStateOf(false)
        }

        val mapInteraction = remember { MutableInteractionSource() }.also {
            if (it.collectIsFocusedAsState().value) {
                isMapExpanded = true
                focusManager.clearFocus(force = true)
            }
        }

        OutlinedTextField(
            value = viewModel.positionName,
            onValueChange = { },
            label = { Text(text = "Local*") },
            placeholder = { Text(text = "Selecione o local") },
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            interactionSource = mapInteraction
        )

        if (isMapExpanded) {
            Dialog(
                properties = DialogProperties(
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                    usePlatformDefaultWidth = false,
                ),
                onDismissRequest = { isMapExpanded = false },
            ) {
                EventLocationInput(
                    currentPosition = viewModel.position,
                    onConfirm = {
                        isMapExpanded = false
                        viewModel.loadPositionName()
                    },
                    onChange = { viewModel.position = it }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventFieldsPreview() {
    MindHubTheme {
        var viewModel = EventViewModel()

        EventFields(viewModel = viewModel)
    }
}