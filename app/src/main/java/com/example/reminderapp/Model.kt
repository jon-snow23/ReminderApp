package com.example.reminderapp


//model class is used to set and get the data from database

class Model {
    private var title: String? = null
    private var date: String? = null
    private var time: String? = null

    constructor()
    constructor(title: String?, date: String?, time: String?) {
        this.title = title
        this.date = date
        this.time = time
    }
}

