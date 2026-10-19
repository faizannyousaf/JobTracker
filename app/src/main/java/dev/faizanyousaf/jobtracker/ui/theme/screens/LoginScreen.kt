package dev.faizanyousaf.jobtracker.ui.theme.screens



import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.R
import dev.faizanyousaf.jobtracker.viewmodel.AuthState
import dev.faizanyousaf.jobtracker.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: AuthViewModel = viewModel()
    val authState by viewModel.authState.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf<String?>(null) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordError by rememberSaveable { mutableStateOf<String?>(null) }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> {
                navController.navigate("homeScreen") {
                    popUpTo("login") { inclusive = true }
                }
            }

            else -> {}
        }
    }


    fun validateAndLogin() {


        var isValid = true

        if (email.isEmpty()) {
            emailError = "Email cannot be empty"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError = "Please enter a valid email"
            isValid = false
        } else {
            emailError = null
        }

        if (password.isEmpty()) {
            passwordError = "Password cannot be empty"
            isValid = false
        } else {
            passwordError = null
        }

        if (isValid) {
            viewModel.login(email, password)
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    })
                }
        ) {
            // your content

            Column(modifier = Modifier.padding(innerpadding)) {
                Image(
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp),
                    painter = painterResource(id = R.drawable.login_screen_logo),
                    contentDescription = stringResource(id = R.string.job_logo)
                )
                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Welcome Back",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.size(10.dp))

                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "Log in to your account",
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

                    Column(modifier = Modifier.padding(20.dp)) {


                        // Firebase error message
                        if (authState is AuthState.Error) {
                            Text(
                                text = (authState as AuthState.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(top = 12.dp)
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 12.dp),
                            text = "Email",
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

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 12.dp),
                                text = "Password",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 18.sp
                            )
                            Text(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .clickable {
                                        navController.navigate("resetPassword")
                                    },
                                text = "Forgot password?",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }

                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            value = password,
                            onValueChange = {
                                password = it
                                passwordError = null
                            },
                            visualTransformation = PasswordVisualTransformation(),
                            isError = passwordError != null,
                            supportingText = {
                                passwordError?.let { Text(it) }
                            },
                            placeholder = { Text(".......") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = Color.LightGray
                            )
                        )

                        Button(
                            onClick = { validateAndLogin() },
                            enabled = authState !is AuthState.Loading,
                            modifier = Modifier
                                .padding(top = 30.dp)
                                .size(width = 500.dp, height = 60.dp),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonColors(
                                MaterialTheme.colorScheme.onBackground,
                                contentColor = MaterialTheme.colorScheme.background ,
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
                                Text("Log in")
                            }
                        }
                    }
                }

                Row(modifier = Modifier.padding(start = 70.dp, bottom = 20.dp, top = 20.dp)) {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.LightGray
                    )
                    Text(
                        modifier = Modifier.clickable { navController.navigate("signup") },
                        text = " Create one",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}