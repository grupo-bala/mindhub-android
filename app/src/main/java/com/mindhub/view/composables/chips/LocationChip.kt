package com.mindhub.view.composables.chips

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LocationChip(
    latitude: Double,
    longitude: Double
) {
    val context = LocalContext.current

    BaseChip(
        modifier = Modifier.clickable {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=$latitude,$longitude")
            ).apply {
                `package` = "com.google.android.apps.maps"
            }

            val pendingIntent = TaskStackBuilder.create(context).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }

            pendingIntent.send()
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.Place,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = "MAPA",
            style = MaterialTheme.typography.labelSmall,
            letterSpacing = 1.2.sp,
            fontWeight = FontWeight.Bold
        )
    }
}