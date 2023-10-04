package ru.kraz.chat.presentation

import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {
    protected open fun setClickListeners() {}
    protected open fun settingViewModel() {}
}