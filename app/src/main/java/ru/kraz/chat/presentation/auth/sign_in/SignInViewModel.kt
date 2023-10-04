package ru.kraz.chat.presentation.auth.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.chat.domain.EventWrapper
import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.auth.sign_in.SignInUseCase
import ru.kraz.chat.presentation.auth.AuthState

class SignInViewModel(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _signInResult = MutableLiveData<EventWrapper<AuthState>>()
    val signInResult: LiveData<EventWrapper<AuthState>> get() = _signInResult

    fun signIn(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _signInResult.postValue(EventWrapper.Single(AuthState.Loading))
        when (val result = signInUseCase(email, password)) {
            is ResultApi.Success -> _signInResult.postValue(EventWrapper.Single(AuthState.Success))
            is ResultApi.Error -> _signInResult.postValue(EventWrapper.Single(AuthState.Error(result.e)))
        }
    }
}