package com.example.reminderapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat


class AlarmBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        val text = bundle!!.getString("event")
        val date = bundle.getString("date") + " " + bundle.getString("time")

        //Click on Notification
        val intent1 = Intent(context, NotificationMessage::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent1.putExtra("message", text)

        //Notification Builder
        val pendingIntent =
            PendingIntent.getActivity(context, 1, intent1,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val mBuilder = NotificationCompat.Builder(context, "notify_001")


        //here we set all the properties for the notification
        val contentView = RemoteViews(context.packageName, R.layout.notification_layout)
        contentView.setImageViewResource(androidx.appcompat.R.id.image, R.drawable.ic_icon)
        val pendingSwitchIntent = PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        contentView.setOnClickPendingIntent(R.id.flashButton, pendingSwitchIntent)
        contentView.setTextViewText(R.id.message, text)
        contentView.setTextViewText(R.id.date, date)
        mBuilder.setSmallIcon(R.drawable.alaram)
        mBuilder.setAutoCancel(true)
        mBuilder.setOngoing(true)
        mBuilder.setAutoCancel(true)
        mBuilder.setPriority(Notification.PRIORITY_HIGH)
        mBuilder.setOnlyAlertOnce(true)
        mBuilder.build().flags = Notification.FLAG_NO_CLEAR or Notification.PRIORITY_HIGH
        mBuilder.setContent(contentView)
        mBuilder.setContentIntent(pendingIntent)

        //we have to create notification channel after api level 26
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channel_id"
            val channel =
                NotificationChannel(channelId, "channel name", NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
            mBuilder.setChannelId(channelId)
        }
        val notification = mBuilder.build()
        notificationManager.notify(1, notification)
    }
}

