package ru.kraz.chat.presentation.auth.sign_in

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        setClickListeners()
        settingViewModel()

        FirebaseAuth.getInstance().currentUser?.let {
            Log.d("attadag", "let")
            launchFragment(ChatFragment.newInstance())
        }
    }

    override fun setClickListeners() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) signInViewModel.signIn(email, password)
        }

        binding.btnSignUp.setOnClickListener {
            launchFragment(SignUpFragment.newInstance())
        }
    }

    override fun settingViewModel() {
        signInViewModel.signInResult.observe(viewLifecycleOwner) {
            it.getContentOrNot {  state ->
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
            SignInFragment()
    }
}