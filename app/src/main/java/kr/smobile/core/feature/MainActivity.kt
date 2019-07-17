package kr.smobile.core.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.SavedStateViewModelFactory
import kr.smobile.core.R

class MainActivity : AppCompatActivity() {

    val viewmodel by viewModels<MainViewModel> { SavedStateViewModelFactory(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
