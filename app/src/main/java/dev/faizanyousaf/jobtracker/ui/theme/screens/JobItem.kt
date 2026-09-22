package dev.faizanyousaf.jobtracker.ui.theme.screens


import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.faizanyousaf.jobtracker.models.JobApplication
import dev.faizanyousaf.jobtracker.models.statusColor
import dev.faizanyousaf.jobtracker.ui.theme.AppliedBg

@Composable
fun JobItem(jobApplication: JobApplication){

        val (bg, accent) = statusColor(jobApplication.status)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(bg, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = jobApplication.company.first().toString(),
                        color = accent,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(jobApplication.company, fontWeight = FontWeight.SemiBold, fontSize = 17.sp)
                    Text(
                        "${jobApplication.role} · ${jobApplication.date}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(bg, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(jobApplication.status,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = accent)
            }
        }
    }

