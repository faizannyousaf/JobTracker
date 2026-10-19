import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.R
import dev.faizanyousaf.jobtracker.models.JobApplication
import dev.faizanyousaf.jobtracker.models.statusColors
import dev.faizanyousaf.jobtracker.repo.AuthRepository
import dev.faizanyousaf.jobtracker.ui.theme.AppliedAccent
import dev.faizanyousaf.jobtracker.ui.theme.AppliedBg
import dev.faizanyousaf.jobtracker.ui.theme.InProcessAccent
import dev.faizanyousaf.jobtracker.ui.theme.InProcessBg

import dev.faizanyousaf.jobtracker.ui.theme.Items.JobItem
import dev.faizanyousaf.jobtracker.ui.theme.OffersAccent
import dev.faizanyousaf.jobtracker.ui.theme.OffersBg
import dev.faizanyousaf.jobtracker.ui.theme.RejectionsAccent
import dev.faizanyousaf.jobtracker.ui.theme.RejectionsBg

import dev.faizanyousaf.jobtracker.ui.theme.screens.StatusPicker
@Composable
fun HomeScreen(navController: NavController) {
    val authRepository = remember { AuthRepository() }
    val jobViewModel: JobViewModel = viewModel()
    val jobs by jobViewModel.jobs.collectAsState()
    var selectedJob by remember { mutableStateOf<JobApplication?>(null) }


    LaunchedEffect(Unit) { jobViewModel.loadJobs() }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            HomeHeader(
                jobCount = jobs.size,
                userInitial = authRepository.userEmail.firstOrNull()?.uppercase() ?: "?",
                onAddClick = { navController.navigate("addJob") },
                onLogout = {
                    authRepository.logout()
                    navController.navigate("login") { popUpTo(0) }
                }
            )

            StatsGrid(jobs)

            RecentApplications(
                jobs = jobs,
                onJobClick = { selectedJob = it },
                onJobDelete = { jobViewModel.deleteJob(it.id) },
                onViewAll = { navController.navigate("viewAll") }
            )
        }

        selectedJob?.let { job ->
            StatusUpdateSheet(
                application = job,
                onDismiss = { selectedJob = null },
                onSave = { newStatus ->  // receive the status

                    jobViewModel.updateJob(job.copy(status = newStatus))

                    Log.d("CLICKKKK",job.status)
                    selectedJob = null
                },
                onDelete = {
                    jobViewModel.deleteJob(job.id)
                    selectedJob = null
                }
            )
        }
    }
}

@Composable
fun HomeHeader(
    jobCount: Int,
    userInitial: String,
    onAddClick: () -> Unit,
    onLogout: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = R.drawable.job_tracker_logo),
            contentDescription = stringResource(id = R.string.job_logo)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(
                modifier = Modifier.padding(start = 7.dp),
                text = "Job Tracker",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(start = 7.dp),
                text = "$jobCount applications",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                color = Color.LightGray
            )
        }

        Box {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(AppliedBg)
                    .border(1.dp, Color(0xFFE5E7EB), CircleShape)
                    .clickable { menuExpanded = true },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userInitial,
                    color = AppliedAccent,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(12.dp),
                tonalElevation = 0.dp,
                shadowElevation = 6.dp,
                border = BorderStroke(1.dp, Color(0xFFF3F4F6))
            ) {
                DropdownMenuItem(
                    text = {
                        Text("Log out", color = Color(0xFFB91C1C), fontWeight = FontWeight.SemiBold)
                    },
                    onClick = {
                        menuExpanded = false
                        onLogout()
                    }
                )
            }
        }

        Spacer(modifier = Modifier.width(10.dp))

        Button(
            onClick = onAddClick,
            modifier = Modifier.size(width = 90.dp, height = 40.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text("+ Add", fontSize = 12.sp)
        }
    }
}

@Composable
fun StatsGrid(jobs: List<JobApplication>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ApplicationCountCard(
                jobs.count { it.status == "Applied" }, "Applied",
                AppliedBg to AppliedAccent, Modifier.weight(1f)
            )
            ApplicationCountCard(
                jobs.count { it.status == "Interview" }, "In Process",
                InProcessBg to InProcessAccent, Modifier.weight(1f)
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            ApplicationCountCard(
                jobs.count { it.status == "Offer" }, "Offers",
                OffersBg to OffersAccent, Modifier.weight(1f)
            )
            ApplicationCountCard(
                jobs.count { it.status == "Rejected" }, "Rejections",
                RejectionsBg to RejectionsAccent, Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ApplicationCountCard(
    count: Int,
    label: String,
    colors: Pair<Color, Color>,
    modifier: Modifier = Modifier
) {
    val (bg, accent) = colors
    Surface(
        modifier = modifier
            .height(100.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
        color = bg,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(count.toString(), fontSize = 30.sp, fontWeight = FontWeight.Bold, color = accent)
            Text(label, fontSize = 20.sp, fontWeight = FontWeight.Light, color = Color.Black)
        }
    }
}

@Composable
fun RecentApplications(
    jobs: List<JobApplication>,
    onJobClick: (JobApplication) -> Unit,
    onJobDelete: (JobApplication) -> Unit,
    onViewAll: () -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
        text = "Recent Applications",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    Column {
        jobs.reversed().take(4).forEach { job ->
            JobItem(
                job,
                onDelete = { onJobDelete(job) },
                onClick = { onJobClick(job) }
            )
            HorizontalDivider(color = Color(0xFFF3F4F6))
        }
    }

    Button(
        onClick = onViewAll,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(60.dp),
        shape = RoundedCornerShape(6.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF9FAFB),
            contentColor = Color.Black
        )
    ) {
        Text("View All")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  StatusUpdateSheet(
    application: JobApplication,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onDelete: () -> Unit
) {
    var selectedStatus by remember(application.id) { mutableStateOf(application.status) }
    var showDeleteConfirm by remember { mutableStateOf(false) }
    val (bg, accent) = statusColors(application.status)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .padding(bottom = 24.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(bg, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = application.company.first().toString(),
                        color = accent,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(application.company, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.background)
                    Text(
                        "${application.role} · Applied ${application.date_applied}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            StatusPicker(
                selectedStatus = selectedStatus,
                onStatusSelected = { selectedStatus = it },

            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onSave(selectedStatus) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground, contentColor = MaterialTheme.colorScheme.background)
            ) {
                Text("Save", fontWeight = FontWeight.Bold)
            }

            TextButton(
                onClick = { showDeleteConfirm = true },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Delete application", color = Color(0xFFB91C1C))
            }
        }
    }

    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("Delete application?") },
            text = { Text("${application.company} will be removed from your list.") },
            confirmButton = {
                TextButton(onClick = {
                    showDeleteConfirm = false
                    onDelete()
                }) { Text("Delete", color = Color(0xFFB91C1C)) }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) { Text("Cancel") }
            }
        )
    }
}