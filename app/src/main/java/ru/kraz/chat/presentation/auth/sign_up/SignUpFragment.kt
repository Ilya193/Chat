package ru.kraz.chat.presentation.auth.sign_up

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.chat.databinding.FragmentSignUpBinding
import ru.kraz.chat.presentation.BaseFragment
import ru.kraz.chat.presentation.auth.AuthState
import ru.kraz.chat.presentation.chat.ChatFragment

class SignUpFragment : BaseFragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
        settingViewModel()
    }

    override fun setClickListeners() {
        binding.btnSignUp.setOnClickListener {
            val nickname = binding.etNickname.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (nickname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty())
                signUpViewModel.signUp(nickname, email, password)
        }
    }

    override fun settingViewModel() {
        signUpViewModel.signUpResult.observe(viewLifecycleOwner) {
            it.getContentOrNot { state ->
                when (state) {
                    is AuthState.Success -> renderSuccess()
                    is AuthState.Loading -> renderLoading()
                    is AuthState.Error -> renderError(state)
                }
            }
        }
    }

    override fun renderSuccess() {
        binding.loading.visibility = View.GONE
        launchFragment(ChatFragment.newInstance())
    }

    override fun renderLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    override fun renderError(state: AuthState.Error) {
        binding.loading.visibility = View.GONE
        Snackbar.make(binding.root, state.msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() =
            SignUpFragment()
    }
}