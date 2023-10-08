package ru.kraz.chat.presentation.auth.sign_in

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.chat.R
import ru.kraz.chat.databinding.FragmentSignInBinding
import ru.kraz.chat.presentation.BaseFragment
import ru.kraz.chat.presentation.auth.AuthState
import ru.kraz.chat.presentation.chat.ChatFragment
import ru.kraz.chat.presentation.auth.sign_up.SignUpFragment
import ru.kraz.chat.presentation.auth.sign_up.SignUpViewModel
import ru.kraz.chat.presentation.chat.MessageUi

class SignInFragment : BaseFragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding!!

    private val signInViewModel: SignInViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val nickname =
                context?.getSharedPreferences("settings", Context.MODE_PRIVATE)?.getString("nickname", "")
                    ?: ""
            launchFragment(ChatFragment.newInstance(nickname))
        }
        else {
            setClickListeners()
            settingViewModel()
        }
    }

    override fun setClickListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) signInViewModel.signIn(email, password)
        }

        binding.btnSignUp.setOnClickListener {
            launchFragment(SignUpFragment.newInstance(), true)
        }
    }

    override fun settingViewModel() {
        signInViewModel.signInResult.observe(viewLifecycleOwner) {
            it.getContentOrNot {  state ->
                when (state) {
                    is AuthState.Success -> renderSuccess(state.nickname)
                    is AuthState.Loading -> renderLoading()
                    is AuthState.Error -> renderError(state)
                }
            }
        }
    }

    private fun renderSuccess(nickname: String) {
        binding.loading.visibility = View.GONE
        launchFragment(ChatFragment.newInstance(nickname))
    }

    private fun renderLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun renderError(state: AuthState.Error) {
        binding.loading.visibility = View.GONE
        Snackbar.make(binding.root, state.msg, Snackbar.LENGTH_SHORT).show()
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