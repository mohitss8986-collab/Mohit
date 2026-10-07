package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SalonRepository

@Composable
fun SalonActionButtons(
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ActionButtonItem(
            icon = Icons.Default.Directions,
            label = "Directions",
            testTag = "action_directions",
            isPrimary = true,
            onClick = {
                openMapLocation(context, SalonRepository.SALON_ADDRESS)
            }
        )

        ActionButtonItem(
            icon = Icons.Default.Navigation,
            label = "Start",
            testTag = "action_start_nav",
            isPrimary = false,
            onClick = {
                startNavigation(context, SalonRepository.SALON_ADDRESS)
            }
        )

        ActionButtonItem(
            icon = Icons.Default.Call,
            label = "Call",
            testTag = "action_call",
            isPrimary = false,
            onClick = {
                makePhoneCall(context, SalonRepository.SALON_PHONE_CLEAN)
            }
        )

        ActionButtonItem(
            icon = if (isSaved) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
            label = if (isSaved) "Saved" else "Save",
            testTag = "action_save",
            isPrimary = isSaved,
            onClick = {
                onToggleSave()
                val msg = if (!isSaved) "Saved to your favorite salons!" else "Removed from favorites"
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            }
        )

        ActionButtonItem(
            icon = Icons.Default.Share,
            label = "Share",
            testTag = "action_share",
            isPrimary = false,
            onClick = {
                shareSalonDetails(context)
            }
        )
    }
}

@Composable
private fun ActionButtonItem(
    icon: ImageVector,
    label: String,
    testTag: String,
    isPrimary: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .testTag(testTag)
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        val containerColor = if (isPrimary) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.surfaceVariant
        }
        val iconColor = if (isPrimary) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.primary
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(containerColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = if (isPrimary) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            fontSize = 12.sp
        )
    }
}

fun openMapLocation(context: Context, address: String) {
    try {
        val gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address))
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(mapIntent)
        } else {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address))
            )
            context.startActivity(browserIntent)
        }
    } catch (_: Exception) {
        val fallback = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(address))
        )
        context.startActivity(fallback)
    }
}

fun startNavigation(context: Context, address: String) {
    try {
        val navUri = Uri.parse("google.navigation:q=" + Uri.encode(address))
        val navIntent = Intent(Intent.ACTION_VIEW, navUri)
        navIntent.setPackage("com.google.android.apps.maps")
        if (navIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(navIntent)
        } else {
            openMapLocation(context, address)
        }
    } catch (_: Exception) {
        openMapLocation(context, address)
    }
}

fun makePhoneCall(context: Context, phoneNumber: String) {
    try {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        context.startActivity(dialIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Could not open phone dialer", Toast.LENGTH_SHORT).show()
    }
}

fun shareSalonDetails(context: Context) {
    val shareText = """
        ✨ Check out ${SalonRepository.SALON_NAME} (${SalonRepository.SALON_HINDI_NAME})!
        ⭐ 3.5 Rating • Hair salon & Beauty Parlour
        📍 Address: ${SalonRepository.SALON_ADDRESS}
        📞 Call: ${SalonRepository.SALON_PHONE}
        📌 Plus code: ${SalonRepository.SALON_PLUS_CODE}
        💇 Services: Haircuts, Make-up, Twist braids, Hairstyling, Blowdry, Curly hair definition & High-Shine Gloss!
    """.trimIndent()

    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareText)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Share Be u Studio Unisex Salon")
    context.startActivity(shareIntent)
}
