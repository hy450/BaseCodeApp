package kr.smobile.core.di

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject
import javax.inject.Provider

class DaggerFragmentFactory
@Inject constructor(
    private val providers: MutableMap<Class<out @JvmSuppressWildcards Fragment>,
            @JvmSuppressWildcards Provider<Fragment>>
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return try {
            val fragmentClass = loadFragmentClass(classLoader, className)
            providers[fragmentClass]?.get()
                ?: throw IllegalStateException("No provider found for $className. " +
                        "Falling back to default factory.")
        } catch (e: Exception) {
            super.instantiate(classLoader, className)
        }
    }
}