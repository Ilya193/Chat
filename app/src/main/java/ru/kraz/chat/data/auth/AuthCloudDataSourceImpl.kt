package ru.kraz.chat.data.auth

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await
import ru.kraz.chat.domain.ResultApi
import java.util.UUID

class AuthCloudDataSourceImpl(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging,
    private val context: Context,
) : AuthCloudDataSource {
    override suspend fun signUp(nickname: String, email: String, password: String): ResultApi<*> {
        auth.createUserWithEmailAndPassword(email, password).await()
        val id = auth.currentUser?.uid ?: UUID.randomUUID().toString()
        firebase.collection("users")
            .document(auth.currentUser?.uid!!)
            .set(UserCloud(id, nickname))
            .await()
        context.getSharedPreferences("settings", Context.MODE_PRIVATE).edit()
            .putString("nickname", nickname).apply()
        messaging.subscribeToTopic("messages").await()
        return ResultApi.Success(nickname)
    }

    override suspend fun signIn(email: String, password: String): ResultApi<*> {
        auth.signInWithEmailAndPassword(email, password).await()
        val nickname =
            context.getSharedPreferences("settings", Context.MODE_PRIVATE).getString("nickname", "")
                ?: ""
        return ResultApi.Success(nickname)
    }
}