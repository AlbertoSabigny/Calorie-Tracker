package com.alberto.calorietracker.authentication.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alberto.calorietracker.authentication.presentation.login.LoginEvent
import com.alberto.calorietracker.authentication.presentation.login.LoginState
import com.alberto.calorietracker.core.presentation.HabitButton
import com.alberto.calorietracker.core.presentation.HabitPasswordTextfield
import com.alberto.calorietracker.core.presentation.HabitTextfield

@Composable
fun LoginForm(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
    onSignUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Box(modifier = Modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = modifier.background(Color(0xFF1E1E1E), shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Log in with Email",
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )
            Divider(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                color = Color.Gray.copy(alpha = 0.5f)
            )
            HabitTextfield(
                value = state.email,
                onValueChange = { onEvent(LoginEvent.EmailChange(it)) },
                placeholder = "Email",
                contentDescription = "Enter email",
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp).padding(horizontal = 20.dp),
                leadingIcon = Icons.Outlined.Email,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                errorMessage = state.emailError,
                isEnabled = !state.isLoading,
                backgroundColor = Color.Black
            )

            HabitPasswordTextfield(
                value = state.password,
                onValueChange = { onEvent(LoginEvent.PasswordChange(it)) },
                contentDescription = "Enter password",
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp).padding(horizontal = 20.dp),
                errorMessage = state.passwordError,
                isEnabled = !state.isLoading,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                    onEvent(LoginEvent.Login)
                }),
                backgroundColor = Color.Black
            )
            HabitButton(
                text = "Login",
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                isEnabled = !state.isLoading
            ) {
                onEvent(LoginEvent.Login)
            }
            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Forgot Password?",
                    color = Color.LightGray,
                    textDecoration = TextDecoration.Underline
                )
            }

            TextButton(onClick = onSignUp) {
                Text(
                    text = buildAnnotatedString {
                        append("Don't have an account? ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Sign up")
                        }
                    },
                    color = Color.LightGray
                )
            }
        }
        if (state.isLoading) {
          //  CircularProgressIndicator(color = Color.White)
        }
    }
}

@Preview
@Composable
fun LoginFormPreview() {
    LoginForm(LoginState(), {}, {})
}
