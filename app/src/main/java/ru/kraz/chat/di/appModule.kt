package ru.kraz.chat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kraz.chat.data.auth.AuthCloudDataSource
import ru.kraz.chat.data.auth.AuthCloudDataSourceImpl
import ru.kraz.chat.data.auth.AuthRepositoryImpl
import ru.kraz.chat.data.chat.ChatCloudDataSource
import ru.kraz.chat.data.chat.ChatCloudDataSourceImpl
import ru.kraz.chat.data.chat.ChatRepositoryImpl
import ru.kraz.chat.data.chat.ChatService
import ru.kraz.chat.domain.ToUiMapper
import ru.kraz.chat.domain.auth.AuthRepository
import ru.kraz.chat.domain.auth.sign_in.SignInUseCase
import ru.kraz.chat.domain.auth.sign_up.SignUpUseCase
import ru.kraz.chat.domain.chat.ChatRepository
import ru.kraz.chat.domain.chat.FetchMessageUseCase
import ru.kraz.chat.domain.chat.MessageDomain
import ru.kraz.chat.domain.chat.SendMessageUseCase
import ru.kraz.chat.domain.chat.SendMessageWithImageUseCase
import ru.kraz.chat.presentation.auth.sign_in.SignInViewModel
import ru.kraz.chat.presentation.auth.sign_up.SignUpViewModel
import ru.kraz.chat.presentation.chat.BaseToMapperUiMapper
import ru.kraz.chat.presentation.chat.ChatViewModel
import ru.kraz.chat.presentation.chat.MessageUi

val appModule = module {
    viewModel<SignUpViewModel> {
        SignUpViewModel(get())
    }

    viewModel<SignInViewModel> {
        SignInViewModel(get())
    }

    viewModel<ChatViewModel> {
        ChatViewModel(get(), get(), get(), get())
    }

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<ChatService> {
        Retrofit.Builder()
            .baseUrl("https://fcm.googleapis.com/fcm/")
            .addConverterFactory(get())
            .build()
            .create(ChatService::class.java)
    }

    factory<SignUpUseCase> {
        SignUpUseCase(get())
    }

    factory<SignInUseCase> {
        SignInUseCase(get())
    }

    factory<SendMessageUseCase> {
        SendMessageUseCase(get())
    }

    factory<FetchMessageUseCase> {
        FetchMessageUseCase(get())
    }

    factory<SendMessageWithImageUseCase> {
        SendMessageWithImageUseCase(get())
    }

    factory<ToUiMapper<MessageDomain, MessageUi>> {
        BaseToMapperUiMapper()
    }

    factory<ChatRepository> {
        ChatRepositoryImpl(get(), get())
    }

    factory<ChatCloudDataSource> {
        ChatCloudDataSourceImpl(get(), get(), get(), get(), get())
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    factory<AuthCloudDataSource> {
        AuthCloudDataSourceImpl(get(), get(), get(), get())
    }

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }

    single<FirebaseMessaging> {
        FirebaseMessaging.getInstance()
    }

    single<FirebaseStorage> {
        FirebaseStorage.getInstance()
    }
}