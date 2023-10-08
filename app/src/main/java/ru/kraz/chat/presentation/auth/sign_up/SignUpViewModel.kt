package ru.kraz.chat.presentation.auth.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.chat.domain.EventWrapper
import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.auth.sign_up.SignUpUseCase
import ru.kraz.chat.presentation.auth.AuthState

class SignUpViewModel(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _signUpResult = MutableLiveData<EventWrapper<AuthState>>()
    val signUpResult: LiveData<EventWrapper<AuthState>> get() = _signUpResult

    fun signUp(nickname: String, email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _signUpResult.postValue(EventWrapper.Single(AuthState.Loading))
        when (val result = signUpUseCase(nickname, email, password)) {
            is ResultApi.Success -> _signUpResult.postValue(EventWrapper.Single(AuthState.Success(result.data as String)))
            is ResultApi.Error -> _signUpResult.postValue(EventWrapper.Single(AuthState.Error(result.e)))
        }
    }
}