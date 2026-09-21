package dev.faizanyousaf.jobtracker.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.R
import dev.faizanyousaf.jobtracker.models.JobApplication
import dev.faizanyousaf.jobtracker.ui.theme.AppliedAccent
import dev.faizanyousaf.jobtracker.ui.theme.AppliedBg
import dev.faizanyousaf.jobtracker.ui.theme.InProcessAccent
import dev.faizanyousaf.jobtracker.ui.theme.InProcessBg
import dev.faizanyousaf.jobtracker.ui.theme.OffersAccent
import dev.faizanyousaf.jobtracker.ui.theme.OffersBg
import dev.faizanyousaf.jobtracker.ui.theme.RejectionsAccent
import dev.faizanyousaf.jobtracker.ui.theme.RejectionsBg

@Composable
fun HomeScreen(navController: NavController){

    var applicationCount by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding->

        Column(modifier = Modifier.padding(innerpadding)
            .fillMaxSize()) {

            Row (modifier = Modifier.fillMaxWidth()
                .padding(top = 20.dp, start = 10.dp)){

                Image(modifier = Modifier.size(60.dp),
                    painter = painterResource(id = R.drawable.job_tracker_logo),
                    contentDescription = stringResource(id = R.string.job_logo)
                )

                Column(modifier = Modifier.weight(1f)) {
                    Text(modifier = Modifier.padding(start = 7.dp),
                        text = "Job Tracker",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(modifier = Modifier.padding(start = 7.dp),
                        text = "$applicationCount applications",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.LightGray
                    )
                }

                Button(onClick = {
                    navController.navigate("+ Add") } ,
                    modifier = Modifier.size(width = 100.dp, height = 40.dp).
                        padding(end = 10.dp),
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonColors(
                        Color.Black,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.Black
                    )
                ){

                    Text("+ Add")
                }

            } //ending top row


            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {

                ApplicationCountCard(0,"Applied",AppliedBg to AppliedAccent)
                ApplicationCountCard(0,"In Process", InProcessBg to InProcessAccent)

            } // first row of job application status ends here

            Spacer(modifier = Modifier.size(10.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {

                ApplicationCountCard(0,"Offers", OffersBg to OffersAccent)
                ApplicationCountCard(0,"In Process", RejectionsBg to RejectionsAccent)

            }// second row ends here

            Spacer(modifier = Modifier.size(10.dp))

            Text(modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                text = "Recent Applications",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            val applications = listOf(
                JobApplication("1", "Meta", "AI Engineer", "Sep 14", "Applied"),
                JobApplication("2", "Google", "Android Engineer", "Sep 14", "Interview"),
                JobApplication("3", "Amazon", "Software Engineer", "Sep 11", "Rejected")
            )
            Column {
                applications.forEach{
                    JobItem(it)
                    HorizontalDivider(color = Color(0xFFF3F4F6))
                }
            }

            Button(onClick = {} ,
                modifier = Modifier.
                padding(10.dp)
                    .size(
                        width = 500.dp,
                        height = 60.dp
                    ),
                shape = RoundedCornerShape(6.dp),
                colors = ButtonColors(
                    Color(0xFFF9FAFB),
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Black,
                    disabledContentColor = Color.Black
                )){

                Text("View All")
            }

        }

    }
}

@Composable
fun ApplicationCountCard(count: Int,label: String, colors: Pair<Color, Color>){

    val (bg, accent) = colors
    Surface (modifier = Modifier
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .width(170.dp)
        .height(100.dp)
        .border(
            width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(8.dp)
        ),
        color = bg,
        shape = RoundedCornerShape(8.dp)

    ){

        Column {
            Text(modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
                text = count.toString(),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = accent
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                text = label,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = Color.Black
            )
        }


    }
}


