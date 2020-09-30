package com.bsk.cointracker

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bsk.cointracker.auth.model.AuthViewModel
import com.bsk.cointracker.auth.model.User
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.databinding.ActivityMainBinding
import com.bsk.cointracker.util.Constants
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FirebaseAuth.AuthStateListener {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var loginCallback: ((Boolean?) -> Unit)? = null

    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)
    }

    private val googleSignInClient: GoogleSignInClient by lazy {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
            .let {
                GoogleSignIn.getClient(this, it)
            }
    }

    private fun signIn() =
        googleSignInClient.signInIntent.also {
            startActivityForResult(it, Constants.REQUEST_CODE_SIGN_IN)
        }

    fun checkIfUserIsAuthenticated(callback: (Boolean?) -> Unit) {
        loginCallback = callback
        authViewModel.checkIfUserIsAuthenticated().observe(this, Observer { user ->
            if (!user.isAuthenticated) {
                signIn()
            } else {
                getUserFromDatabase(user.uid)
            }
        })
    }

    private fun getUserFromDatabase(uid: String) {
        authViewModel.setUid(uid).observe(this, Observer {
            if (it.status == ApiResult.Status.SUCCESS) {
                loginCallback?.invoke(true)
            } else if (it.status == ApiResult.Status.ERROR) {
                loginError(it.message)
            }
        })
    }

    private fun loginError(message: String?) {
        loginCallback?.invoke(null)
        loginCallback = null
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getGoogleAuthCredential(googleSignInAccount: GoogleSignInAccount) {
        googleSignInAccount.idToken.run {
            GoogleAuthProvider.getCredential(this, null)
        }.let {
            signInWithGoogleAuthCredential(it)
        }
    }

    private fun signInWithGoogleAuthCredential(googleAuthCredential: AuthCredential) {
        authViewModel.signInWithGoogle(googleAuthCredential).observe(this, Observer {
            if (it.status == ApiResult.Status.SUCCESS) {
                val authenticatedUser = it.data
                if (authenticatedUser?.isNew == true) {
                    createNewUser(authenticatedUser)
                } else {
                    loginCallback?.invoke(true)
                }
            } else if (it.status == ApiResult.Status.ERROR) {
                loginError(it.message)
            }
        })
    }

    private fun createNewUser(authenticatedUser: User) {
        authViewModel.createUser(authenticatedUser).observe(this, Observer {
            if (it.status == ApiResult.Status.SUCCESS) {
                loginCallback?.invoke(true)
            } else if (it.status == ApiResult.Status.ERROR) {
                loginError(it.message)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val googleSignInAccount = task.getResult(
                    ApiException::class.java
                )
                googleSignInAccount?.let { getGoogleAuthCredential(it) }
            } catch (e: ApiException) {
                loginError(e.message)
                Timber.e(e)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(this)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(this)
    }

    override fun onAuthStateChanged(p0: FirebaseAuth) {
        firebaseAuth.currentUser ?: run {
            firebaseAuth.signOut()
            googleSignInClient.signOut()
        }
    }
}