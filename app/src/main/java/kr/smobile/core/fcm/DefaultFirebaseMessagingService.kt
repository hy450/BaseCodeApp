package kr.smobile.core.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kr.smobile.sample.R
import kr.smobile.feature.MainActivity
import timber.log.Timber
import java.util.*

class DefaultFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = this::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        /*
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(applicationContext,"onMessageReceived From: " + remoteMessage.priority, Toast.LENGTH_SHORT).show()
        }
         */

        debugLog(remoteMessage)
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            showNotification(remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: " + it.body)

        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG,"onNewToken : $token")
    }

    private fun debugLog(remoteMessage: RemoteMessage) {
        Log.d(TAG,"From: ${remoteMessage.from} , Priority: ${remoteMessage.priority}, OriginalPriority: ${remoteMessage.originalPriority} MessageType: ${remoteMessage.messageType}, TO: ${remoteMessage.to}")
        Log.d(TAG,"MessageID: ${remoteMessage.messageId}")

        val receivedTime = Date().time
        val diffTime = receivedTime - remoteMessage.sentTime



        Timber.d("message received timems: $receivedTime title: ${remoteMessage.data.get("title")}  created at timems: ${remoteMessage.sentTime}  delay ms: ${diffTime}")
    }

    private fun showNotification(data: Map<String,String>) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(applicationContext,channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val channel = NotificationChannel(channelId,channelName,
                NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())

    }

}