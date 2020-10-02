package com.bsk.cointracker

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.bsk.cointracker.auth.AuthViewModel
import com.bsk.cointracker.data.remote.common.ApiResult
import com.bsk.cointracker.databinding.ActivityMainBinding
import com.bsk.cointracker.util.Constants
import com.bsk.cointracker.util.showToast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

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

        checkIfUserIsAuthenticated(false)
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

    fun checkIfUserIsAuthenticated(showLogin: Boolean, callback: ((Boolean?) -> Unit)? = null) {
        loginCallback = callback
        authViewModel.checkIfUserIsAuthenticated2().observe(this, Observer {
            if (it) {
                loginSucceeded()
                return@Observer
            }

            if (showLogin) {
                signIn()
                return@Observer
            }

            loginCallback?.invoke(false)
        })
    }

    private fun signInWithGoogleAuthToken(idToken: String) {
        authViewModel.signInWithGoogle2(idToken).observe(this, Observer {
            if (it.status == ApiResult.Status.SUCCESS) {
                loginSucceeded()
                showToast(getString(R.string.message_login_success))
            } else if (it.status == ApiResult.Status.ERROR) {
                loginFailed(it.message)
            }
        })
    }

    private fun loginSucceeded() {
        loginCallback?.invoke(true)
    }

    private fun loginFailed(message: String?) {
        Timber.e(message)
        loginCallback?.invoke(null)
        loginCallback = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_SIGN_IN) {
            try {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                val result = task.getResult(ApiException::class.java)!!
                signInWithGoogleAuthToken(result.idToken!!)
            } catch (e: ApiException) {
                loginFailed(e.message)
                Timber.e(e)
            }
        }
    }
}