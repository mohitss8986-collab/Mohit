package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SalonRepository

@Composable
fun SalonInfoCards(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Address Item
            InfoRowItem(
                icon = Icons.Default.LocationOn,
                title = "Address",
                content = SalonRepository.SALON_ADDRESS,
                testTag = "info_address",
                onClick = {
                    openMapLocation(context, SalonRepository.SALON_ADDRESS)
                },
                trailingAction = {
                    IconButton(
                        onClick = {
                            copyToClipboard(context, "Salon Address", SalonRepository.SALON_ADDRESS)
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy address",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

            // Phone Item
            InfoRowItem(
                icon = Icons.Default.Call,
                title = "Phone",
                content = SalonRepository.SALON_PHONE,
                testTag = "info_phone",
                onClick = {
                    makePhoneCall(context, SalonRepository.SALON_PHONE_CLEAN)
                },
                trailingAction = {
                    IconButton(
                        onClick = {
                            makePhoneCall(context, SalonRepository.SALON_PHONE_CLEAN)
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Call",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

            // Plus Code Item
            InfoRowItem(
                icon = Icons.Default.PinDrop,
                title = "Plus Code",
                content = SalonRepository.SALON_PLUS_CODE,
                testTag = "info_plus_code",
                onClick = {
                    copyToClipboard(context, "Plus Code", SalonRepository.SALON_PLUS_CODE)
                },
                trailingAction = {
                    IconButton(
                        onClick = {
                            copyToClipboard(context, "Plus Code", SalonRepository.SALON_PLUS_CODE)
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy plus code",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            )

            Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

            // Timings Item
            InfoRowItem(
                icon = Icons.Default.AccessTime,
                title = "Opening Hours",
                content = SalonRepository.SALON_HOURS,
                testTag = "info_hours",
                onClick = {},
                trailingAction = null
            )
        }
    }
}

@Composable
private fun InfoRowItem(
    icon: ImageVector,
    title: String,
    content: String,
    testTag: String,
    onClick: () -> Unit,
    trailingAction: (@Composable () -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (trailingAction != null) {
            trailingAction()
        }
    }
}

fun copyToClipboard(context: Context, label: String, text: String) {
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(label, text)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(context, "$label copied to clipboard", Toast.LENGTH_SHORT).show()
}
