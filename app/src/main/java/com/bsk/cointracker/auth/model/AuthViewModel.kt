package com.bsk.cointracker.auth.model

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsk.cointracker.data.remote.repository.AuthRepository
import com.google.firebase.auth.AuthCredential


class AuthViewModel @ViewModelInject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun signInWithGoogle(googleAuthCredential: AuthCredential) =
        authRepository.signInWithGoogle(viewModelScope, googleAuthCredential)

    fun createUser(authenticatedUser: User) =
        authRepository.createUserIfNotExists(viewModelScope, authenticatedUser)

    fun checkIfUserIsAuthenticated() =
        authRepository.checkIfUserIsAuthenticated(viewModelScope)

    fun setUid(uid: String) =
        authRepository.addUserToLiveData(viewModelScope, uid)
}