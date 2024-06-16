package com.example.reminderapp

import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar


//this class is to add the take the reminders from the user and inserts into database
class ReminderActivity : AppCompatActivity() {
    private var mSubmitbtn: Button? = null
    private var mDatebtn: Button? = null
    private var mTimebtn: Button? = null
    private var mTitledit: EditText? = null
    private var timeTonotify: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        mTitledit = findViewById<View>(R.id.editTitle) as EditText
        mDatebtn =
            findViewById<View>(R.id.btnDate) as Button //assigned all the material reference to get and set data
        mTimebtn = findViewById<View>(R.id.btnTime) as Button
        mSubmitbtn = findViewById<View>(R.id.btnSubmit) as Button
        mTimebtn!!.setOnClickListener {
            selectTime() //when we click on the choose time button it calls the select time method
        }
        mDatebtn!!.setOnClickListener { selectDate() } //when we click on the choose date button it calls the select date method

        mSubmitbtn!!.setOnClickListener {
            val title = mTitledit!!.getText().toString()
                .trim { it <= ' ' } //access the data form the input field
            val date = mDatebtn!!.getText().toString()
                .trim { it <= ' ' } //access the date form the choose date button
            val time = mTimebtn!!.getText().toString()
                .trim { it <= ' ' } //access the time form the choose time button
            if (title.isEmpty()) {
                Toast.makeText(applicationContext, "Please Enter text", Toast.LENGTH_SHORT)
                    .show() //shows the toast if input field is empty
            } else {
                if (time == "time" || date == "date") {                                               //shows toast if date and time are not selected
                    Toast.makeText(
                        applicationContext,
                        "Please select date and time",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    processinsert(title, date, time)
                }
            }
        }
    }

    private fun processinsert(title: String, date: String, time: String) {
        val result: String = DbManager(this).AddReminder(
            title,
            date,
            time
        ) //inserts the title,date,time into sql lite database
        setAlarm(title, date, time) //calls the set alarm method to set alarm
        mTitledit!!.setText("")
        Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
    }

    private fun selectTime() {                                                                     //this method performs the time picker task
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(this,
            { timePicker, i, i1 ->
                timeTonotify = "$i:$i1" //temp variable to store the time to set alarm
                mTimebtn!!.text = formatTime(i, i1) //sets the button text as selected time
            }, hour, minute, false
        )
        timePickerDialog.show()
    }

    private fun selectDate() {                                                                     //this method performs the date picker task
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH]
        val day = calendar[Calendar.DAY_OF_MONTH]
        val datePickerDialog = DatePickerDialog(this,
            { datePicker, year, month, day ->
                mDatebtn!!.text =
                    day.toString() + "-" + (month + 1) + "-" + year //sets the selected date as test for button
            }, year, month, day
        )
        datePickerDialog.show()
    }

    private fun formatTime(
        hour: Int,
        minute: Int
    ): String {                                                //this method converts the time into 12hr farmat and assigns am or pm
        var time: String = ""
        val formattedMinute: String = if (minute / 10 == 0) {
            "0$minute"
        } else {
            "" + minute
        }
        time = if (hour == 0) {
            "12:$formattedMinute AM"
        } else if (hour < 12) {
            "$hour:$formattedMinute AM"
        } else if (hour == 12) {
            "12:$formattedMinute PM"
        } else {
            val temp = hour - 12
            "$temp:$formattedMinute PM"
        }
        return time
    }

    private fun setAlarm(text: String, date: String, time: String) {
        val am =
            getSystemService(ALARM_SERVICE) as AlarmManager //assigining alaram manager object to set alaram
        val intent = Intent(
            applicationContext,
            AlarmBroadcast::class.java
        )
        intent.putExtra(
            "event",
            text
        ) //sending data to alarm class to create channel and notification
        intent.putExtra("time", date)
        intent.putExtra("date", time)
        val pendingIntent =
            PendingIntent.getBroadcast(applicationContext, 0, intent,
                PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        val dateandtime = "$date $timeTonotify"
        val formatter: DateFormat = SimpleDateFormat("d-M-yyyy hh:mm")
        try {
            val date1 = formatter.parse(dateandtime)
            am[AlarmManager.RTC_WAKEUP, date1.time] = pendingIntent
            Toast.makeText(applicationContext, "Alaram", Toast.LENGTH_SHORT).show()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val intentBack = Intent(
            applicationContext,
            MainActivity::class.java
        ) //this intent will be called once the setting alaram is completes
        intentBack.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intentBack) //navigates from adding reminder activity ot mainactivity
    }
}