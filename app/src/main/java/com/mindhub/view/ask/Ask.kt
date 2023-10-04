package com.mindhub.view.ask

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mindhub.services.CurrentUser
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun Ask() {
    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = CurrentUser!!.username)
        }
    }
}