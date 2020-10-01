package com.bsk.cointracker.data.remote.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bsk.cointracker.data.remote.common.ApiResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    val user
        get() = firebaseAuth.currentUser

    fun signInWithGoogle2(
        scope: CoroutineScope,
        idToken: String
    ): LiveData<ApiResult<Boolean>> =
        liveData(scope.coroutineContext) {
            emit(ApiResult.loading())

            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                firebaseAuth.signInWithCredential(credential).await()
                emit(ApiResult.success(firebaseAuth.currentUser != null))
            } catch (e: Exception) {
                emit(ApiResult.error(e.message.toString()))
                Timber.e(e)
            }
        }

    fun checkIfUserIsAuthenticated2(scope: CoroutineScope): LiveData<Boolean> =
        liveData(scope.coroutineContext) {
            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser == null) {
                emit(false)
                return@liveData
            }

            emit(true)
        }
}