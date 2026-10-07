package com.example.data

import com.example.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object SalonRepository {
    const val SALON_NAME = "Be u Studio Unisex Salon"
    const val SALON_HINDI_NAME = "बे यू स्टूडियो यूनिसेक्स सैलून"
    const val SALON_CATEGORY = "Hair salon & Beauty Parlour"
    const val SALON_PHONE = "082181 59881"
    const val SALON_PHONE_CLEAN = "08218159881"
    const val SALON_ADDRESS = "7th Avenue High Street Markit, Greater Noida W Rd, Ghaziabad, Uttar Pradesh 201016"
    const val SALON_PLUS_CODE = "JC6H+46 Ghaziabad, Uttar Pradesh"
    const val SALON_HOURS = "10:00 AM – 09:30 PM (Open Daily)"

    val stylists = listOf(
        Stylist("any", "Any Available Specialist", "Recommended for quickest slot", 4.9f, listOf("All Services")),
        Stylist("s1", "Rohit Verma", "Master Hair Stylist & Barber", 4.8f, listOf("Hairstyling", "Blowdry", "Fades")),
        Stylist("s2", "Priya Sen", "Creative Color & Braids Expert", 4.7f, listOf("Twist braids", "Gloss", "Curly hair")),
        Stylist("s3", "Megha Sharma", "Senior Make-up & Bridal Artist", 4.9f, listOf("Make-up", "Bridal", "Facials"))
    )

    val allServices = listOf(
        // Hairstyling & Cuts
        SalonService(
            id = "srv_twist_braids",
            name = "Twist Braids Styling",
            category = ServiceCategory.HAIRSTYLING,
            price = 799,
            durationMinutes = 60,
            description = "Intricate trendy twist braiding customized to hair length and texture. Neat, long-lasting and protective style.",
            gender = TargetGender.UNISEX,
            isPopular = true,
            tags = listOf("Twist braids", "Styling", "Popular"),
            imageResId = R.drawable.img_hair_style
        ),
        SalonService(
            id = "srv_blowdry",
            name = "Signature Blowdry & Setting",
            category = ServiceCategory.HAIRSTYLING,
            price = 449,
            durationMinutes = 40,
            description = "Volume blowout or sleek straight finish with thermal hair protection spray and mirror gloss shine.",
            gender = TargetGender.WOMEN,
            isPopular = true,
            tags = listOf("Blowdry", "Gloss", "Hairstyling"),
            imageResId = R.drawable.img_hair_style
        ),
        SalonService(
            id = "srv_curly_hair",
            name = "Curly Hair Definition & Styling",
            category = ServiceCategory.HAIRSTYLING,
            price = 699,
            durationMinutes = 50,
            description = "Curl hydration, diffuser drying, bounce enhancement and frizz-control leave-in care.",
            gender = TargetGender.UNISEX,
            isPopular = true,
            tags = listOf("Curly hair", "Styling"),
            imageResId = R.drawable.img_hair_style
        ),
        SalonService(
            id = "srv_unisex_haircut",
            name = "Classic Unisex Precision Haircut",
            category = ServiceCategory.HAIRSTYLING,
            price = 299,
            durationMinutes = 35,
            description = "Personalized consultation, precision hair cut, shampoo wash and blowdry styling by our senior artist.",
            gender = TargetGender.UNISEX,
            isPopular = true,
            tags = listOf("Haircut", "Hairstyling"),
            imageResId = R.drawable.img_hair_style
        ),

        // Make-up & Bridal
        SalonService(
            id = "srv_makeup_glam",
            name = "Party Glam Make-up",
            category = ServiceCategory.MAKEUP,
            price = 1499,
            durationMinutes = 75,
            description = "Flawless HD skin base, smokey/soft glam eye makeup, lipstick contour and long-stay fixing mist.",
            gender = TargetGender.WOMEN,
            isPopular = true,
            tags = listOf("Make-up", "HD Glam", "Party"),
            imageResId = R.drawable.img_makeup_studio
        ),
        SalonService(
            id = "srv_bridal_makeup",
            name = "Bridal & Engagement Make-up",
            category = ServiceCategory.MAKEUP,
            price = 4999,
            durationMinutes = 120,
            description = "Full bridal package including premium HD base, eyelashes, drape styling, nail paint and hairstyle setting.",
            gender = TargetGender.WOMEN,
            isPopular = false,
            tags = listOf("Make-up", "Bridal", "Luxe"),
            imageResId = R.drawable.img_makeup_studio
        ),

        // Hair Treatments & Gloss
        SalonService(
            id = "srv_hair_gloss",
            name = "High-Shine Hair Gloss Treatment",
            category = ServiceCategory.HAIR_TREATMENTS,
            price = 1199,
            durationMinutes = 45,
            description = "Semi-permanent gloss treatment that coats the hair cuticle, restoring luminosity, softness, and depth.",
            gender = TargetGender.UNISEX,
            isPopular = true,
            tags = listOf("Gloss", "Hair Care", "Shine"),
            imageResId = R.drawable.img_hair_style
        ),
        SalonService(
            id = "srv_keratin_spa",
            name = "Luxe Keratin Smoothing & Spa",
            category = ServiceCategory.HAIR_TREATMENTS,
            price = 2499,
            durationMinutes = 90,
            description = "Deep nourishing keratin protein therapy for frizzy, dull hair. Silky smooth finish lasting up to 8 weeks.",
            gender = TargetGender.UNISEX,
            isPopular = false,
            tags = listOf("Keratin", "Spa", "Smoothing"),
            imageResId = R.drawable.img_hair_style
        ),

        // Skin & Facials
        SalonService(
            id = "srv_o3_facial",
            name = "O3+ Radiance Glow Facial",
            category = ServiceCategory.SKIN_FACIAL,
            price = 1299,
            durationMinutes = 60,
            description = "Multi-step brightening facial with exfoliation, vacuum cleanse, vitamin serum massage, and peel-off mask.",
            gender = TargetGender.UNISEX,
            isPopular = true,
            tags = listOf("Facial", "Glow", "Skin"),
            imageResId = R.drawable.img_hero_salon
        ),
        SalonService(
            id = "srv_detan_pack",
            name = "Insta De-Tan & Skin Cleanup",
            category = ServiceCategory.SKIN_FACIAL,
            price = 599,
            durationMinutes = 40,
            description = "Removes sun damage, clears dead cells, unblocks pores and revitalizes tired skin tone.",
            gender = TargetGender.UNISEX,
            isPopular = false,
            tags = listOf("De-Tan", "Cleanup"),
            imageResId = R.drawable.img_hero_salon
        ),

        // Men's Grooming
        SalonService(
            id = "srv_beard_sculpt",
            name = "Beard Sculpting & Hot Towel Trim",
            category = ServiceCategory.MENS_GROOMING,
            price = 199,
            durationMinutes = 25,
            description = "Precise razor edge lining, trimming, conditioning beard oil, and soothing hot towel wrap.",
            gender = TargetGender.MEN,
            isPopular = true,
            tags = listOf("Beard", "Grooming"),
            imageResId = R.drawable.img_hero_salon
        ),
        SalonService(
            id = "srv_mens_combo",
            name = "Men's Complete Grooming Combo",
            category = ServiceCategory.MENS_GROOMING,
            price = 599,
            durationMinutes = 55,
            description = "Haircut + Beard Styling + Head Massage with Ayurvedic cooling oil + Face De-tan wash.",
            gender = TargetGender.MEN,
            isPopular = true,
            tags = listOf("Haircut", "Beard", "Head Massage"),
            imageResId = R.drawable.img_hero_salon
        ),

        // Nails & Spa
        SalonService(
            id = "srv_pedicure_spa",
            name = "Relaxing Foot Spa & Pedicure",
            category = ServiceCategory.SPA_NAILS,
            price = 549,
            durationMinutes = 45,
            description = "Aromatic foot soak, dead skin buffing, cuticle care, relaxing acupressure massage and polish.",
            gender = TargetGender.UNISEX,
            isPopular = false,
            tags = listOf("Pedicure", "Foot Spa"),
            imageResId = R.drawable.img_hero_salon
        )
    )

    val initialReviews = listOf(
        ReviewItem(
            id = "rev_1",
            authorName = "Samiksha Yadav",
            reviewCount = 1,
            photoCount = 0,
            rating = 4.0f,
            timeAgo = "6 months ago",
            comment = "Visited for multiple hair styling services. The team did a very clean job with twist braids and blowdry. The gloss finish on hair looked radiant and stayed fresh for days. Professional attitude and cozy salon setup.",
            services = listOf("Make-up", "Twist braids", "Hairstyling", "Blowdry", "Curly hair", "Gloss"),
            helpfulCount = 4
        ),
        ReviewItem(
            id = "rev_2",
            authorName = "Trapti Mishra",
            reviewCount = 5,
            photoCount = 2,
            rating = 3.0f,
            timeAgo = "a day ago",
            comment = "Neat ambiance inside 7th Avenue High Street market. The haircut was quick and well executed. Staff is polite. Would love to try the facial treatment next time.",
            services = listOf("Hairstyling", "Haircut"),
            helpfulCount = 1
        )
    )

    val nearbyBranches = listOf(
        NearbySalonBranch(
            name = "Be U Smart Unisex Salon Mahagun Mywoods Mart",
            rating = 4.8f,
            reviewsCount = 1082,
            category = "Beauty Parlour",
            distance = "1.2 km away",
            address = "Gaur City 2, Greater Noida West"
        ),
        NearbySalonBranch(
            name = "Be U Natural Salon - Gaur City Mall",
            rating = 4.1f,
            reviewsCount = 407,
            category = "Beauty Parlour",
            distance = "1.8 km away",
            address = "Gaur City Mall, Noida Extension"
        ),
        NearbySalonBranch(
            name = "Unify Studio Unisex Salon",
            rating = 4.6f,
            reviewsCount = 13,
            category = "Beauty Parlour",
            distance = "2.3 km away",
            address = "Sector 16B, Greater Noida West"
        ),
        NearbySalonBranch(
            name = "B4U Unisex Salon",
            rating = 4.6f,
            reviewsCount = 11,
            category = "Hairdresser",
            distance = "2.9 km away",
            address = "Eco Village Market, Greater Noida"
        ),
        NearbySalonBranch(
            name = "Be U Salons - New Style",
            rating = 4.0f,
            reviewsCount = 37,
            category = "Beauty Parlour",
            distance = "3.4 km away",
            address = "Gaur Saundaryam Mart, Techzone 4"
        )
    )

    val specialOffers = listOf(
        SalonOffer(
            id = "off_1",
            title = "Festive Makeover Combo",
            subtitle = "Party Make-up + Blowdry + Hair Gloss",
            originalPrice = 3147,
            offerPrice = 1999,
            badge = "SAVE 36%",
            services = listOf("Party Glam Make-up", "Signature Blowdry & Setting", "High-Shine Hair Gloss Treatment")
        ),
        SalonOffer(
            id = "off_2",
            title = "Executive Grooming Duo",
            subtitle = "Haircut + Beard Styling + De-tan Cleanup",
            originalPrice = 1097,
            offerPrice = 699,
            badge = "SAVE 35%",
            services = listOf("Classic Unisex Precision Haircut", "Beard Sculpting & Hot Towel Trim", "Insta De-Tan & Skin Cleanup")
        )
    )

    // Dynamic State
    private val _reviews = MutableStateFlow(initialReviews)
    val reviews: StateFlow<List<ReviewItem>> = _reviews.asStateFlow()

    private val _isSaved = MutableStateFlow(false)
    val isSaved: StateFlow<Boolean> = _isSaved.asStateFlow()

    private val _appointments = MutableStateFlow(
        listOf(
            SalonAppointment(
                id = "APT-7842",
                customerName = "Mohit Sharma",
                customerPhone = "082181 59881",
                serviceNames = listOf("Twist Braids Styling", "Signature Blowdry & Setting"),
                stylistName = "Priya Sen",
                date = "Today",
                timeSlot = "04:30 PM",
                totalAmount = 1248,
                notes = "Prefer light serum finish",
                status = AppointmentStatus.CONFIRMED
            )
        )
    )
    val appointments: StateFlow<List<SalonAppointment>> = _appointments.asStateFlow()

    fun toggleSaved() {
        _isSaved.update { !it }
    }

    fun addReview(authorName: String, rating: Float, comment: String, selectedServices: List<String>) {
        val newReview = ReviewItem(
            id = "rev_${System.currentTimeMillis()}",
            authorName = if (authorName.isBlank()) "Happy Customer" else authorName.trim(),
            reviewCount = 1,
            photoCount = 0,
            rating = rating,
            timeAgo = "Just now",
            comment = comment.trim(),
            services = selectedServices,
            helpfulCount = 0
        )
        _reviews.update { listOf(newReview) + it }
    }

    fun bookAppointment(
        customerName: String,
        customerPhone: String,
        serviceNames: List<String>,
        stylistName: String,
        date: String,
        timeSlot: String,
        totalAmount: Int,
        notes: String
    ): SalonAppointment {
        val newApt = SalonAppointment(
            id = "APT-${(1000..9999).random()}",
            customerName = customerName.ifBlank { "Guest Client" },
            customerPhone = customerPhone.ifBlank { "082181 59881" },
            serviceNames = serviceNames,
            stylistName = stylistName,
            date = date,
            timeSlot = timeSlot,
            totalAmount = totalAmount,
            notes = notes,
            status = AppointmentStatus.CONFIRMED
        )
        _appointments.update { listOf(newApt) + it }
        return newApt
    }

    fun cancelAppointment(id: String) {
        _appointments.update { list ->
            list.map { if (it.id == id) it.copy(status = AppointmentStatus.CANCELLED) else it }
        }
    }
}
