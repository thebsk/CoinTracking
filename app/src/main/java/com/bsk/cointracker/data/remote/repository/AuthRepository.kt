package com.bsk.cointracker.data.remote.repository


import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bsk.cointracker.auth.model.User
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.util.Constants
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    firestore: FirebaseFirestore
) {
    private val usersRef = firestore.collection(Constants.KEY_USERS)

    fun signInWithGoogle(
        scope: CoroutineScope,
        googleAuthCredential: AuthCredential
    ): LiveData<ApiResult<User>> =
        liveData(scope.coroutineContext) {
            emit(ApiResult.loading())

            try {
                val result = firebaseAuth.signInWithCredential(googleAuthCredential).await()

                val isNewUser: Boolean =
                    result?.additionalUserInfo?.isNewUser ?: false

                val firebaseUser: FirebaseUser? = firebaseAuth.currentUser

                if (firebaseUser != null) {
                    val uid: String = firebaseUser.uid
                    val name: String? = firebaseUser.displayName
                    val email: String? = firebaseUser.email
                    val user = User(uid, name, email)
                    user.isNew = isNewUser
                    emit(ApiResult.success(user))
                }
            } catch (e: Exception) {
                emit(ApiResult.error(e.message.toString()))
                Timber.e(e)
            }
        }

    fun createUserIfNotExists(
        scope: CoroutineScope,
        authenticatedUser: User
    ): LiveData<ApiResult<User>> =
        liveData(scope.coroutineContext) {
            emit(ApiResult.loading())
            val uidRef = usersRef.document(authenticatedUser.uid)

            withContext(Dispatchers.IO) {
                try {
                    uidRef.get().await().let {
                        if (!it.exists()) {
                            uidRef.set(authenticatedUser).await()
                            authenticatedUser.isCreated = true
                        }
                    }
                    emit(ApiResult.success(authenticatedUser))
                } catch (e: Exception) {
                    emit(ApiResult.error(e.message.toString()))
                    Timber.e(e)
                }
            }
        }

    val user = User()

    fun checkIfUserIsAuthenticated(scope: CoroutineScope): LiveData<User> =
        liveData(scope.coroutineContext) {
            user.apply {
                firebaseAuth.currentUser?.let {
                    uid = it.uid
                    isAuthenticated = true
                    emit(this)
                } ?: run {
                    isAuthenticated = false
                    emit(this)
                }
            }
        }

    fun addUserToLiveData(scope: CoroutineScope, uid: String): LiveData<ApiResult<User?>> =
        liveData(scope.coroutineContext) {
            emit(ApiResult.loading())

            withContext(Dispatchers.IO) {
                try {
                    usersRef.document(uid).get().await().let {
                        if (it.exists()) {
                            it.toObject(User::class.java).also { user ->
                                emit(ApiResult.success(user))
                            }
                        } else {
                            throw IllegalStateException("User not found! Probably manually deleted from Firestore.")
                        }
                    }
                } catch (e: Exception) {
                    emit(ApiResult.error(e.message.toString()))
                    Timber.e(e)
                }
            }
        }
}