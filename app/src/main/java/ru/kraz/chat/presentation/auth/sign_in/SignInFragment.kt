package ru.kraz.chat.presentation.auth.sign_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.chat.R
import ru.kraz.chat.databinding.FragmentSignInBinding
import ru.kraz.chat.presentation.BaseFragment
import ru.kraz.chat.presentation.auth.sign_up.SignUpFragment
import ru.kraz.chat.presentation.auth.sign_up.SignUpViewModel

class SignInFragment : BaseFragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        settingViewModel()
    }

    override fun setClickListeners() {
        binding.btnSignIn.setOnClickListener {

        }

        binding.btnSignUp.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignUpFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun settingViewModel() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            SignInFragment()
    }
}