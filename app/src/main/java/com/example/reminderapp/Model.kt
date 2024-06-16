package com.example.reminderapp


//model class is used to set and get the data from database

class Model {
     var title: String? = null
        private set
     var date: String? = null
        private set
    var time: String? = null
        private set

    constructor()

    constructor(title: String?, date: String?, time: String?) {
        this.title = title
        this.date = date
        this.time = time
    }
}

