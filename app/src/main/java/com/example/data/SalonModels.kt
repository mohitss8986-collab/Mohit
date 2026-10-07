package com.example.data

enum class TargetGender(val label: String) {
    UNISEX("Unisex"),
    WOMEN("Women"),
    MEN("Men")
}

enum class ServiceCategory(val displayName: String, val iconName: String) {
    ALL("All Services", "Stars"),
    HAIRSTYLING("Hair & Styling", "ContentCut"),
    MAKEUP("Make-up & Bridal", "Face"),
    HAIR_TREATMENTS("Treatments & Gloss", "Spa"),
    SKIN_FACIAL("Skin & Facials", "CleanHands"),
    MENS_GROOMING("Men's Grooming", "Person"),
    SPA_NAILS("Nails & Spa", "AutoAwesome")
}

data class SalonService(
    val id: String,
    val name: String,
    val category: ServiceCategory,
    val price: Int,
    val durationMinutes: Int,
    val description: String,
    val gender: TargetGender = TargetGender.UNISEX,
    val isPopular: Boolean = false,
    val tags: List<String> = emptyList(),
    val imageResId: Int? = null
)

data class ReviewItem(
    val id: String,
    val authorName: String,
    val reviewCount: Int = 1,
    val photoCount: Int = 0,
    val rating: Float,
    val timeAgo: String,
    val comment: String,
    val services: List<String> = emptyList(),
    val helpfulCount: Int = 0
)

enum class AppointmentStatus(val label: String) {
    CONFIRMED("Confirmed"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled")
}

data class SalonAppointment(
    val id: String,
    val customerName: String,
    val customerPhone: String,
    val serviceNames: List<String>,
    val stylistName: String,
    val date: String,
    val timeSlot: String,
    val totalAmount: Int,
    val notes: String = "",
    val status: AppointmentStatus = AppointmentStatus.CONFIRMED,
    val createdAtMillis: Long = System.currentTimeMillis()
)

data class Stylist(
    val id: String,
    val name: String,
    val title: String,
    val rating: Float,
    val specialties: List<String>
)

data class NearbySalonBranch(
    val name: String,
    val rating: Float,
    val reviewsCount: Int,
    val category: String,
    val distance: String,
    val address: String
)

data class SalonOffer(
    val id: String,
    val title: String,
    val subtitle: String,
    val originalPrice: Int,
    val offerPrice: Int,
    val badge: String,
    val services: List<String>
)
