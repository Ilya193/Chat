package ru.kraz.chat.presentation.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.chat.domain.ResultApi
import ru.kraz.chat.domain.ToUiMapper
import ru.kraz.chat.domain.chat.FetchMessageUseCase
import ru.kraz.chat.domain.chat.MessageDomain
import ru.kraz.chat.domain.chat.SendMessageUseCase
import ru.kraz.chat.domain.chat.SendMessageWithImageUseCase

class ChatViewModel(
    private val sendMessageUseCase: SendMessageUseCase,
    private val fetchMessageUseCase: FetchMessageUseCase,
    private val sendMessageWithImageUseCase: SendMessageWithImageUseCase,
    private val mapper: ToUiMapper<MessageDomain, MessageUi>,
) : ViewModel() {

    private val _chatResult = MutableLiveData<ChatState>()
    val chatResult: LiveData<ChatState> get() = _chatResult

    fun sendMessage(message: String) = viewModelScope.launch(Dispatchers.IO) {
        sendMessageUseCase(message)
    }

    fun sendMessageWithImage(message: String, filename: String, imgUri: String) =
        viewModelScope.launch(Dispatchers.IO) {
            sendMessageWithImageUseCase(message, filename, imgUri)
        }

    fun fetchMessage() = viewModelScope.launch(Dispatchers.IO) {
        _chatResult.postValue(ChatState.Loading)
        fetchMessageUseCase().collect { result ->
            when (result) {
                is ResultApi.Success -> {
                    val messages = result.data.map {
                        mapper.map(it)
                    }.reversed()
                    _chatResult.postValue(ChatState.Success(messages))
                }

                is ResultApi.Error -> _chatResult.postValue(ChatState.Error(result.e))
            }
        }
    }
}