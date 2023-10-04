package ru.kraz.chat.data.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.kraz.chat.domain.ResultApi
import java.util.UUID

class CloudDataSourceImpl(
    private val firebase: FirebaseFirestore,
    private val auth: FirebaseAuth,
) : CloudDataSource {
    override suspend fun signUp(nickname: String, email: String, password: String): ResultApi<*> {
        auth.createUserWithEmailAndPassword(email, password).await()
        val id = auth.currentUser?.uid ?: UUID.randomUUID().toString()
        firebase.collection("users")
            .document(auth.currentUser?.uid!!)
            .set(UserCloud(id, nickname))
            .await()
        return ResultApi.Success(Unit)
    }
}