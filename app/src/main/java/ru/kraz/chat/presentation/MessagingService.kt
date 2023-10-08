package ru.kraz.chat.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.kraz.chat.R

class MessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("attadag", "onNewToken: $token")
    }

    private val auth = FirebaseAuth.getInstance()

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val senderId = message.data["senderId"]
        val currentId = auth.currentUser?.uid
        if (senderId != currentId)
            createNotification(message.notification?.title!!, message.notification?.body!!)
    }

    private fun createNotification(title: String, body: String) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        val manager = createNotificationChannel()
        manager.notify(1, notification.build())
    }

    private fun createNotificationChannel(): NotificationManager {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "mainChannel",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        return notificationManager
    }

    private companion object {
        private const val channelId = "chat_id"
    }
}