package ru.kraz.chat.presentation

import androidx.fragment.app.Fragment
import ru.kraz.chat.R
import ru.kraz.chat.presentation.auth.AuthState
import ru.kraz.chat.presentation.chat.MessageUi

open class BaseFragment : Fragment() {
    protected open fun setClickListeners() {}
    protected open fun settingViewModel() {}
    protected open fun launchFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val fragmentManager =
            parentFragmentManager.beginTransaction().replace(R.id.fragmentContainer, fragment)
        if (addToBackStack)
            fragmentManager.addToBackStack(null)
        fragmentManager.commit()
    }
}