package com.bsk.cointracker.auth.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.repository.AuthRepository


class AuthViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun signInWithGoogle2(idToken: String) =
        authRepository.signInWithGoogle2(viewModelScope, idToken)

    fun checkIfUserIsAuthenticated2() =
        authRepository.checkIfUserIsAuthenticated2(viewModelScope)
}