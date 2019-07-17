package kr.smobile.core.feature.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateViewModelFactory
import kr.smobile.core.R
import kr.smobile.core.feature.MainViewModel


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    val viewmodel by viewModels<HomeViewModel> { SavedStateViewModelFactory(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


}
