package com.example.cannabisautomatico

import android.app.*
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import org.apache.harmony.awt.internal.nls.Messages.getString


class notificacionesHelper : Application{
    private val pendingIntent: PendingIntent? = null
    private val CHANNEL_ID = "NOTIFICACION"
    private val NOTIFICACION_ID = 0
    var context: Context
    var title: String
    var descripcion: String

    constructor(context: Context, title: String, descripion: String) {
        this.context = context
        this.title = title
        this.descripcion = descripion
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel();
        createNotification();
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Noticacion"
            val notificationChannel =
                NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun createNotification() {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, CHANNEL_ID
        )
        builder.setSmallIcon(R.drawable.hoja)
        builder.setContentTitle(title)
        builder.setContentText(descripcion)
        builder.color = Color.BLUE
        builder.priority = NotificationCompat.PRIORITY_DEFAULT
        builder.setLights(Color.MAGENTA, 1000, 1000)
        builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        builder.setDefaults(Notification.DEFAULT_SOUND)
        val notificationManagerCompat = NotificationManagerCompat.from(
            applicationContext
        )
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build())
    }
    }


