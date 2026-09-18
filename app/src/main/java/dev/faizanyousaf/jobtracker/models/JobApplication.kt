package dev.faizanyousaf.jobtracker.models

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
    val id: String,
    val company: String,
    val role: String,
    val date: String,
    val status: String
)

fun statusColor(status: String): Pair<Color, Color> = when (status) {
    "Applied" -> AppliedBg to AppliedAccent
    "Interview" -> InProcessBg to InProcessAccent
    "Offer" -> OffersBg to OffersAccent
    "Rejected" -> RejectionsBg to RejectionsAccent
    else -> Color.LightGray to Color.DarkGray
}

