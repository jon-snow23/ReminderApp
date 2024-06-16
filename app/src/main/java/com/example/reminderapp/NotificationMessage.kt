package com.example.reminderapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NotificationMessage : AppCompatActivity() {
    private var textView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification_message)
        textView = findViewById(R.id.tv_message)
        val bundle = intent.extras

        if (bundle != null) {
            val message = bundle.getString("message")
            textView?.text = message ?: "No message received"
        }
    }
}

