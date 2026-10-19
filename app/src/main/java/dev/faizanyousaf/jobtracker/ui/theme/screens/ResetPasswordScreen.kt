package dev.faizanyousaf.jobtracker.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.R
import dev.faizanyousaf.jobtracker.viewmodel.AuthState
import dev.faizanyousaf.jobtracker.viewmodel.AuthViewModel

@Composable
fun ResetPasswordScreen(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()
    val authState by viewModel.authState.collectAsState()

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var linkSent by remember { mutableStateOf(false) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.PasswordResetSent -> {
                linkSent = true
            }
            else -> {}
        }
    }

    fun validateAndSend() {
        if (email.isEmpty()) {
            emailError = "Email cannot be empty"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Please enter a valid email"
        } else {
            emailError = null
            viewModel.resetPassword(email)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->

        Column(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 40.dp),
                painter = painterResource(id = R.drawable.email_logo),
                contentDescription = stringResource(id = R.string.job_logo)
            )
            Spacer(modifier = Modifier.size(10.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Reset Password",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "We'll send you a link to reset",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )

            Surface(
                modifier = Modifier
                    .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                    .border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp)
            ) {

                if (!linkSent) {
                    Column(modifier = Modifier.padding(20.dp)) {

                        Spacer(modifier = Modifier.size(10.dp))

                        // Firebase error message
                        if (authState is AuthState.Error) {
                            Text(
                                text = (authState as AuthState.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "Email address",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            value = email,
                            onValueChange = {
                                email = it
                                emailError = null
                            },
                            isError = emailError != null,
                            supportingText = {
                                emailError?.let { Text(it) }
                            },
                            placeholder = { Text("you@example.com") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = Color.LightGray
                            )
                        )

                        Button(
                            onClick = { validateAndSend() },
                            enabled = authState !is AuthState.Loading,
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .size(width = 500.dp, height = 60.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonColors(
                                Color.Black,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Gray,
                                disabledContentColor = Color.White
                            )
                        ) {
                            if (authState is AuthState.Loading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text("Send Reset Link")
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                    ) {

                        Image(
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 40.dp),
                            painter = painterResource(id = R.drawable.check_icon),
                            contentDescription = stringResource(id = R.string.job_logo)
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            textAlign = TextAlign.Center,
                            text = "Check your inbox",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            textAlign = TextAlign.Center,
                            text = "If your account exists, you'll receive a password reset link",
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )

                        Button(
                            onClick = { navController.navigate("login") },
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .size(width = 500.dp, height = 60.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonColors(
                                Color.Black,
                                contentColor = Color.White,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text("Back to login")
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    modifier = Modifier.size(22.dp),
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back"
                )

                Text(
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    },
                    text = " Back to login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )
            }
        }
    }
}