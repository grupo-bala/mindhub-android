package com.mindhub.view.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mindhub.ui.theme.MindHubTheme

@Composable
fun Tabs(
    tabs: List<String>,
    tabsContent: List<@Composable () -> Unit>
) {
    var currentTab by rememberSaveable {
        mutableStateOf(0)
    }

    Column {
        TabRow(
            selectedTabIndex = currentTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = currentTab == index,
                    onClick = { currentTab = index },
                    text = { Text(text = title) }
                )
            }
        }

        tabsContent[currentTab]()
    }
}

@Preview(showBackground = true)
@Composable
fun TabsPreview() {
    MindHubTheme {
        Tabs(
            tabs = listOf("Tab 1", "Tab 2", "Tab 3"),
            tabsContent = listOf(
                { Text(text = "Dentro da tab 1") },
                { Text(text = "Dentro da tab 2") },
                { Text(text = "Dentro da tab 3") }
            )
        )
    }
}