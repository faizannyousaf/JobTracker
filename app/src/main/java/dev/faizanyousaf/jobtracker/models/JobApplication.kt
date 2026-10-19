package dev.faizanyousaf.jobtracker.models

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.faizanyousaf.jobtracker.ui.theme.AppliedAccent
import dev.faizanyousaf.jobtracker.ui.theme.AppliedBg
import dev.faizanyousaf.jobtracker.ui.theme.InProcessAccent
import dev.faizanyousaf.jobtracker.ui.theme.InProcessBg
import dev.faizanyousaf.jobtracker.ui.theme.OffersAccent
import dev.faizanyousaf.jobtracker.ui.theme.OffersBg
import dev.faizanyousaf.jobtracker.ui.theme.RejectionsAccent
import dev.faizanyousaf.jobtracker.ui.theme.RejectionsBg


data class JobApplication(
    val id: Int,
    val user_id: String,
    val company: String,
    val role: String,
    val date_applied: String,
    val status: String
)

@Composable
fun statusColors(status: String): Pair<Color, Color> {
    val isDark = isSystemInDarkTheme()
    return when (status) {
        "Applied" -> if (isDark) Color(0xFF1E2A44) to Color(0xFF60A5FA)
        else AppliedBg to AppliedAccent
        "In Process" -> if (isDark) Color(0xFF3A2A12) to Color(0xFFFBBF24)
        else InProcessBg to InProcessAccent
        "Interview" -> if (isDark) Color(0xFF3A2A12) to Color(0xFFFBBF24)
        else InProcessBg to InProcessAccent
        "Offer" -> if (isDark) Color(0xFF14301F) to Color(0xFF4ADE80)
        else OffersBg to OffersAccent
        "Rejected" -> if (isDark) Color(0xFF3A1A1A) to Color(0xFFF87171)
        else RejectionsBg to RejectionsAccent
        else -> if (isDark) Color(0xFF2A2A2A) to Color(0xFF9CA3AF)
        else Color.LightGray to Color.Gray
    }
}