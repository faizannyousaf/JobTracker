package dev.faizanyousaf.jobtracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dev.faizanyousaf.jobtracker.navigation.NavGraph
import dev.faizanyousaf.jobtracker.repo.AuthRepository
import dev.faizanyousaf.jobtracker.ui.theme.JobTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobTrackerTheme {

                val navController = rememberNavController()
                val repository = AuthRepository()
                if(repository.currentUser()!= null){
                    NavGraph( navController, "homeScreen")
                }
                else{
                    NavGraph( navController, "login")
                }

            }
        }
    }
}

