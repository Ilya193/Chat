package ru.kraz.chat.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kraz.chat.data.auth.AuthRepositoryImpl
import ru.kraz.chat.data.auth.CloudDataSource
import ru.kraz.chat.data.auth.CloudDataSourceImpl
import ru.kraz.chat.domain.auth.AuthRepository
import ru.kraz.chat.domain.auth.sign_up.SignUpUseCase
import ru.kraz.chat.presentation.auth.sign_up.SignUpViewModel

val appModule = module {
    viewModel<SignUpViewModel> {
        SignUpViewModel(get())
    }

    factory<SignUpUseCase> {
        SignUpUseCase(get())
    }

    factory<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    factory<CloudDataSource> {
        CloudDataSourceImpl(get(), get())
    }

    single<FirebaseFirestore> {
        FirebaseFirestore.getInstance()
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
}