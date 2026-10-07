package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookOnline
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ContentCut
import androidx.compose.material.icons.outlined.EventNote
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.SalonOffer
import com.example.data.SalonRepository
import com.example.data.SalonService
import com.example.ui.components.BookingBottomSheet
import com.example.ui.components.makePhoneCall
import com.example.ui.components.shareSalonDetails
import com.example.ui.screens.LocationContactScreen
import com.example.ui.screens.MyAppointmentsScreen
import com.example.ui.screens.SalonOverviewScreen
import com.example.ui.screens.ServicesScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

enum class SalonScreenTab(val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector) {
    OVERVIEW("Salon", Icons.Filled.Storefront, Icons.Outlined.Storefront),
    SERVICES("Services", Icons.Filled.ContentCut, Icons.Outlined.ContentCut),
    APPOINTMENTS("Bookings", Icons.Filled.EventNote, Icons.Outlined.EventNote),
    LOCATION("Location", Icons.Filled.LocationOn, Icons.Outlined.LocationOn)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainSalonApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainSalonApp() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var currentTab by remember { mutableStateOf(SalonScreenTab.OVERVIEW) }
    val appointments by SalonRepository.appointments.collectAsState()
    val isSaved by SalonRepository.isSaved.collectAsState()

    // Booking BottomSheet state
    val bookingSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBookingSheet by remember { mutableStateOf(false) }
    val preselectedServices = remember { mutableStateListOf<SalonService>() }

    // System BackHandler to return to OVERVIEW tab if on another tab
    if (currentTab != SalonScreenTab.OVERVIEW) {
        BackHandler {
            currentTab = SalonScreenTab.OVERVIEW
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Be u Studio",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            SalonRepository.toggleSaved()
                            coroutineScope.launch {
                                val msg = if (!isSaved) "Salon saved to your favorites!" else "Salon removed from favorites"
                                snackbarHostState.showSnackbar(msg)
                            }
                        },
                        modifier = Modifier.testTag("topbar_btn_save")
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = "Save salon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { shareSalonDetails(context) },
                        modifier = Modifier.testTag("topbar_btn_share")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    IconButton(
                        onClick = { makePhoneCall(context, SalonRepository.SALON_PHONE_CLEAN) },
                        modifier = Modifier.testTag("topbar_btn_call")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = "Call salon",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        floatingActionButton = {
            if (currentTab == SalonScreenTab.OVERVIEW || currentTab == SalonScreenTab.LOCATION) {
                ExtendedFloatingActionButton(
                    onClick = {
                        preselectedServices.clear()
                        showBookingSheet = true
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Filled.BookOnline,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    },
                    text = {
                        Text(
                            text = "Book Appointment",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.testTag("fab_quick_book")
                )
            }
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp
            ) {
                SalonScreenTab.values().forEach { tab ->
                    val isSelected = currentTab == tab
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentTab = tab },
                        icon = {
                            if (tab == SalonScreenTab.APPOINTMENTS && appointments.isNotEmpty()) {
                                BadgedBox(badge = {
                                    val confirmedCount = appointments.count { it.status == com.example.data.AppointmentStatus.CONFIRMED }
                                    if (confirmedCount > 0) {
                                        Badge { Text("$confirmedCount") }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                        contentDescription = tab.title
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                                    contentDescription = tab.title
                                )
                            }
                        },
                        label = {
                            Text(
                                text = tab.title,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                    )
                }
            }
        }
    ) { innerPadding ->
        when (currentTab) {
            SalonScreenTab.OVERVIEW -> {
                SalonOverviewScreen(
                    onNavigateToServices = { currentTab = SalonScreenTab.SERVICES },
                    onBookService = { service ->
                        preselectedServices.clear()
                        if (service != null) {
                            preselectedServices.add(service)
                        }
                        showBookingSheet = true
                    },
                    onOpenOfferBooking = { offer ->
                        preselectedServices.clear()
                        val offerServices = SalonRepository.allServices.filter { srv ->
                            offer.services.contains(srv.name)
                        }
                        preselectedServices.addAll(offerServices)
                        showBookingSheet = true
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            SalonScreenTab.SERVICES -> {
                ServicesScreen(
                    onProceedToBook = { services ->
                        preselectedServices.clear()
                        preselectedServices.addAll(services)
                        showBookingSheet = true
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            SalonScreenTab.APPOINTMENTS -> {
                MyAppointmentsScreen(
                    onBookNewClick = {
                        preselectedServices.clear()
                        showBookingSheet = true
                    },
                    modifier = Modifier.padding(innerPadding)
                )
            }

            SalonScreenTab.LOCATION -> {
                LocationContactScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    // Modal BottomSheet for Booking Flow
    if (showBookingSheet) {
        BookingBottomSheet(
            sheetState = bookingSheetState,
            initialSelectedServices = preselectedServices.toList(),
            onDismiss = { showBookingSheet = false },
            onBookingSuccess = { appointment ->
                showBookingSheet = false
                currentTab = SalonScreenTab.APPOINTMENTS
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Appointment ${appointment.id} booked successfully!")
                }
            }
        )
    }
}
