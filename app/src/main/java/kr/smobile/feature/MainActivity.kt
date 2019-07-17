package kr.smobile.feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import kr.smobile.R


class MainActivity : AppCompatActivity() {

    val viewmodel by viewModels<MainViewModel> { SavedStateViewModelFactory(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
