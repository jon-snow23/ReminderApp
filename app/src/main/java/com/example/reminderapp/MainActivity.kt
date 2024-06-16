package com.example.reminderapp

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    var mCreateRem: FloatingActionButton? = null
    var mRecyclerview: RecyclerView? = null
    var dataholder = ArrayList<Model>() //Array list to add reminders and display in recyclerview
    var adapter:myAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerview = findViewById<View>(R.id.recyclerView) as RecyclerView
        mRecyclerview!!.setLayoutManager(LinearLayoutManager(applicationContext))
        mCreateRem =
            findViewById<View>(R.id.create_reminder) as FloatingActionButton //Floating action button to change activity
        mCreateRem!!.setOnClickListener {
            val intent = Intent(
                applicationContext,
                ReminderActivity::class.java
            )
            startActivity(intent) //Starts the new activity to add Reminders
        }
        val cursor: Cursor =
            DbManager(applicationContext).ReadAllReminder() //Cursor To Load data From the database
        while (cursor.moveToNext()) {
            val model = Model(cursor.getString(1), cursor.getString(2), cursor.getString(3))
            dataholder.add(model)
        }
        adapter = myAdapter(dataholder)
        mRecyclerview!!.setAdapter(adapter) //Binds the adapter with recyclerview
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        finish() //Makes the user to exit form the app
        super.onBackPressed()
    }
}