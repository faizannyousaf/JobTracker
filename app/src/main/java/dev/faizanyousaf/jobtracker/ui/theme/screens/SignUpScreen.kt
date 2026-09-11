package dev.faizanyousaf.jobtracker.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.faizanyousaf.jobtracker.R


@Composable
fun SignUpScreen(navController: NavController){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize(),) { innerpadding ->

        Column(modifier = Modifier.padding(innerpadding)) {
            Image(modifier = Modifier.size(120.dp)
                .align(Alignment.CenterHorizontally)
                .padding(top = 40.dp),
                painter = painterResource(id = R.drawable.signup_logo),
                contentDescription = stringResource(id = R.string.job_logo)
            )
            Spacer(modifier = Modifier.size(10.dp))

            Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Create your account",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(10.dp))
4
            Text(modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Sign up to get started",
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

                Column(modifier = Modifier.padding(20.dp)) {

                    Button(onClick = {} ,
                        modifier = Modifier.border( width = 1.dp,
                            color = Color.LightGray)
                            .size(
                                width = 500.dp,
                                height = 60.dp
                            ),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonColors(
                            Color.Transparent,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Transparent,
                            disabledContentColor = Color.Transparent
                        )) {

                        Row(modifier = Modifier.padding(10.dp)) {
                            Image(modifier = Modifier.size(25.dp),
                                painter = painterResource(id = R.drawable.google_logo),
                                contentDescription = stringResource(id = R.string.job_logo)
                            )
                            Spacer(modifier = Modifier.size(25.dp))

                            Text( text = "Continue with Google",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium)
                        }

                    }
                    Spacer(modifier = Modifier.size(10.dp))

                    Row() {
                        HorizontalDivider(
                            modifier = Modifier.size(width = 150.dp, height = 20.dp)
                                .padding(top = 16.dp, end = 10.dp),
                            color = Color.LightGray,
                            thickness = 1.dp
                        )
                        Text(modifier = Modifier.padding(top = 5.dp), text = "OR",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.LightGray
                        )
                        HorizontalDivider(
                            modifier = Modifier.size(
                                width = 170.dp,
                                height = 20.dp
                            )
                                .padding(top = 16.dp, start = 10.dp),
                            color = Color.LightGray,
                            thickness = 1.dp
                        )

                    }

                    Text(modifier = Modifier.padding(top = 12.dp),
                        text = "Email",
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

                    Text(modifier = Modifier.padding(top = 12.dp),
                        text = "Password",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp),
                        value = password,
                        onValueChange = { password = it },
                        visualTransformation = PasswordVisualTransformation(),
                        placeholder = { Text(".......") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray)
                    )

                    Text(modifier = Modifier.padding(top = 12.dp),
                        text = "Confirm password",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 10.dp),
                        value = password,
                        onValueChange = { password = it },
                        visualTransformation = PasswordVisualTransformation(),
                        placeholder = { Text(".......") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedBorderColor = Color.LightGray,
                            focusedBorderColor = Color.LightGray)
                    )


                    Button(onClick = {} ,
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

                        Text("Create account")
                    }


                }

            }
            Row(modifier = Modifier.padding(start = 70.dp, bottom = 20.dp,top = 20.dp)){
                Text( text = "Already have an account?",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.LightGray
                )
                Text(modifier = Modifier.clickable { navController.navigate("login") }, text = " Log in",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

            }
        }


    }




}