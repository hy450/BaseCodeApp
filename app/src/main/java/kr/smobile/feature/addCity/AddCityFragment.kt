package kr.smobile.feature.addCity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kr.smobile.core.di.withFactory
import kr.smobile.core.platform.BaseFragment
import kr.smobile.sample.R
import kr.smobile.sample.databinding.FragmentAddCityBinding
import timber.log.Timber
import javax.inject.Inject


class AddCityFragment @Inject constructor(
    addCityViewModelFactory: AddCityViewModel.Factory
): BaseFragment<AddCityViewModel, FragmentAddCityBinding>(R.layout.fragment_add_city) {

    val REQ_GOOGLE_SIGN_IN = 0

    override val viewModel: AddCityViewModel by viewModels { withFactory(addCityViewModelFactory) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().log("Higgs-Boson detected! Bailing out")

        viewModel.navigatorLiveData.observe(viewLifecycleOwner,::navigate)
    }

    private fun navigate(event: AddCityViewModel.NavigatorEvent) {
        when(event) {
            AddCityViewModel.NavigatorEvent.RequestGoogleSignIn -> {
                googleSignIn()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Timber.i("onActivityResult :: $requestCode ")
        if( requestCode == REQ_GOOGLE_SIGN_IN ) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    // google 로 로그인 하기
    private fun googleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_default_web_client_id))
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(requireContext(),gso)

        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, REQ_GOOGLE_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account =
                completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            account?.let {
                Timber.d("google account email ${it.email} : ${it.id} ${it.idToken}")
            }

        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.w("signInResult:failed code=" + e.statusCode)
        }
    }
}