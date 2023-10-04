package ru.kraz.chat.presentation

import androidx.fragment.app.Fragment
import ru.kraz.chat.R
import ru.kraz.chat.presentation.auth.AuthState
import ru.kraz.chat.presentation.auth.sign_up.SignUpFragment

open class BaseFragment : Fragment() {
    protected open fun setClickListeners() {}
    protected open fun settingViewModel() {}
    protected open fun launchFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    protected open fun renderSuccess() {}
    protected open fun renderLoading() {}
    protected open fun renderError(state: AuthState.Error) {}
}