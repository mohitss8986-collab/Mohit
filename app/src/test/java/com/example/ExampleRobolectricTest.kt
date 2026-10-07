package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.AppointmentStatus
import com.example.data.SalonRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Be u Studio", appName)
    }

    @Test
    fun `salon repository has pre-populated services and reviews`() {
        val services = SalonRepository.allServices
        assertTrue("Services list should not be empty", services.isNotEmpty())
        assertTrue("Contains Twist Braids", services.any { it.name.contains("Twist Braids") })
        assertTrue("Contains Blowdry", services.any { it.name.contains("Blowdry") })

        val reviews = SalonRepository.reviews.value
        assertEquals(2, reviews.size)
        assertEquals("Samiksha Yadav", reviews[0].authorName)
        assertEquals("Trapti Mishra", reviews[1].authorName)
    }

    @Test
    fun `book appointment adds to repository`() {
        val initialCount = SalonRepository.appointments.value.size
        val newApt = SalonRepository.bookAppointment(
            customerName = "Test Client",
            customerPhone = "082181 59881",
            serviceNames = listOf("Twist Braids Styling"),
            stylistName = "Priya Sen",
            date = "Today",
            timeSlot = "02:30 PM",
            totalAmount = 799,
            notes = "First visit"
        )

        assertNotNull(newApt.id)
        assertEquals(initialCount + 1, SalonRepository.appointments.value.size)
        assertEquals(AppointmentStatus.CONFIRMED, newApt.status)
    }
}
