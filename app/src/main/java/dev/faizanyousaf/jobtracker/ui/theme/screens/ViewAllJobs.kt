package dev.faizanyousaf.jobtracker.ui.theme.screens

import JobViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.models.statusColor
import dev.faizanyousaf.jobtracker.ui.theme.Items.JobItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewAllJobs(
    navController: NavController
) {

    val jobViewModel: JobViewModel = viewModel()
    val jobs by jobViewModel.jobs.collectAsState()

    jobViewModel.loadJobs()

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf("All") }

    val filters = listOf("All", "Applied", "Interview", "Offer", "Rejected")

    // Derived list: search + filter applied together
    val filteredApplications = jobs.filter { application ->
        val matchesFilter = selectedFilter == "All" || application.status == selectedFilter
        val matchesSearch = searchQuery.isBlank() ||
                application.company.contains(searchQuery, ignoreCase = true) ||
                application.role.contains(searchQuery, ignoreCase = true)
        matchesFilter && matchesSearch
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar =     {
            TopAppBar(
                title = { Text("View All Jobs", fontSize = 18.sp)},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            modifier = Modifier.size(22.dp),
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ){innerPadding ->

        Column(modifier = Modifier.fillMaxSize().
            padding(innerPadding)
            .padding(10.dp)) {


            // Search field
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search company or role...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Filter chips row, with counts
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    val count = if (filter == "All") jobs.size
                    else jobs.count { it.status == filter }
                    val isSelected = filter == selectedFilter
                    val (bg, accent) = if (filter == "All") {
                        Color(0xFF111827) to Color.White
                    } else {
                        statusColor(filter)
                    }

                    Box(
                        modifier = Modifier
                            .background(
                                color = if (isSelected) bg else bg.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .clickable { selectedFilter = filter }
                            .padding(horizontal = 14.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = "$filter · $count",
                            color = accent,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Filtered list
            if (filteredApplications.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No applications match your search.", color = Color.Gray, fontSize = 14.sp)
                }
            } else {
                LazyColumn {
                    items(filteredApplications) { application ->
                        JobItem(application)
                        HorizontalDivider(color = Color(0xFFF3F4F6))
                    }
                }
            }
        }
    }

}