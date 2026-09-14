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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.R

@Composable
fun ResetPasswordScreen(navController: NavController){


    var email by remember { mutableStateOf("") }
    var linkSent by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerpadding ->

        Column(modifier = Modifier.padding(innerpadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(modifier = Modifier.size(120.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp),
                painter = painterResource(id = R.drawable.email_logo),
                contentDescription = stringResource(id = R.string.job_logo)
            )
            Spacer(modifier = Modifier.size(10.dp))

            Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Reset Password",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(10.dp))

            Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "We'll send you a link to reset",
                fontSize = 20.sp,
                fontWeight = FontWeight.Light
            )


            Surface (modifier = Modifier
                .padding(top = 50.dp, start = 20.dp, end = 20.dp)
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ),
                shape = RoundedCornerShape(8.dp)

            ){

                if(!linkSent){
                    Column(modifier = Modifier.padding(20.dp)) {

                        Spacer(modifier = Modifier.size(10.dp))

                        Text(modifier = Modifier.padding(top = 12.dp),
                            text = "Email address",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth()
                                .padding(top = 10.dp),
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("you@example.com") },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.LightGray,
                                focusedBorderColor = Color.LightGray
                            )
                        )


                        Button(onClick = {
                            linkSent = true
                        } ,
                            modifier = Modifier.
                            padding(top = 30.dp)
                                .size(
                                    width = 500.dp,
                                    height = 60.dp
                                ),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonColors(
                                Color.Black,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Black,
                                disabledContentColor = Color.Black
                            )){

                            Text("Send Reset Link")
                        }

                    }
                }
                else {
                    Column(modifier = Modifier.padding(20.dp)
                        .fillMaxWidth()) {

                        Image(modifier = Modifier.size(120.dp)
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 40.dp),
                            painter = painterResource(id = R.drawable.check_icon),
                            contentDescription = stringResource(id = R.string.job_logo)
                        )

                        Text(modifier = Modifier.fillMaxWidth()
                            .padding(top = 12.dp),
                            textAlign = TextAlign.Center,
                            text = "Check your inbox",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )

                        Text(modifier = Modifier.fillMaxWidth()
                            .padding(top = 12.dp),
                            textAlign = TextAlign.Center,
                            text = "if your account exist, you'll receive a password reset link",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 18.sp
                        )


                        Button(onClick = {
                        } ,
                            modifier = Modifier.
                            padding(top = 30.dp)
                                .size(
                                    width = 500.dp,
                                    height = 60.dp
                                ),
                            shape = RoundedCornerShape(6.dp),
                            colors = ButtonColors(
                                Color.Black,
                                contentColor = Color.White,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.White
                            )){

                            Text("Back to login")
                        }

                    }
                }

            }


            Row(modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center){

                Image(modifier = Modifier.size(22.dp), imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back")

                Text( modifier = Modifier.
                clickable {
                    navController.navigate("login")
                }, text = " Back to login",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

            }

        }

    }


}