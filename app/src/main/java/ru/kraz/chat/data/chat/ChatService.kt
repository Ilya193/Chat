package ru.kraz.chat.data.chat

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatService {

    @POST("send")
    @Headers("Authorization: key=AAAAFS9vJ4E:APA91bFiODrOD0NkG-6VAk9uQe6xnjizlleVQKnWB-C_yFQeFaeeniVcUp3U6rSi2PxiiUXe_NaKIIgqnaBOKOb01ptWW8VEKHaSDDUxnMdmwzAvhz8mI23g8C4Ibmk5FoJi1HnjyNlX", "Content-Type: application/json")
    suspend fun sendMessage(@Body message: SendMessageCloud)
}