package dev.faizanyousaf.jobtracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.faizanyousaf.jobtracker.repo.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepository()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.signUp(email, password)
            _authState.value = if (result.isSuccess) {
                AuthState.Success
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Sign up failed")
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.login(email, password)
            _authState.value = if (result.isSuccess) {
                AuthState.Success
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = repository.resetPassword(email)
            _authState.value = if (result.isSuccess) {
                AuthState.PasswordResetSent
            } else {
                AuthState.Error(result.exceptionOrNull()?.message ?: "Reset failed")
            }
        }
    }

    fun resetState() {
        _authState.value = AuthState.Idle
    }

}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    object PasswordResetSent : AuthState()
    data class Error(val message: String) : AuthState()
}