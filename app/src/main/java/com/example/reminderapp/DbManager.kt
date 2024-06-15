package com.example.reminderapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbManager(context: Context?) :
    SQLiteOpenHelper(context, dbname, null, 1) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {                                           //sql query to insert data in sqllite
        val query =
            "create table tbl_reminder(id integer primary key autoincrement,title text,date text,time text)"
        sqLiteDatabase.execSQL(query)
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        val query =
            "DROP TABLE IF EXISTS tbl_reminder" //sql query to check table with the same name or not
        sqLiteDatabase.execSQL(query) //executes the sql command
        onCreate(sqLiteDatabase)
    }

    fun AddReminder(title: String?, date: String?, time: String?): String {
        val database = this.readableDatabase
        val contentValues = ContentValues()
        contentValues.put("title", title) //Inserts  data into sqllite database
        contentValues.put("date", date)
        contentValues.put("time", time)
        val result = database.insert("tbl_reminder", null, contentValues)
            .toFloat() //returns -1 if data successfully inserts into database
        return if (result == -1f) {
            "Failed"
        } else {
            "Successfully inserted"
        }
    }

    fun ReadAllReminder(): Cursor {
        val database = this.writableDatabase
        val query =
            "select * from tbl_reminder order by id desc" //Sql query to  retrieve  data from the database
        return database.rawQuery(query, null)
    }

    companion object {
        private const val dbname = "reminder" //Table  name to store reminders in sqllite
    }
}


