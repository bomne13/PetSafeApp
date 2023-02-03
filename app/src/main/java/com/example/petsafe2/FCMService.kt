package com.example.petsafe2

import android.app.Service
import android.content.Intent
import android.os.IBinder

class FCMService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }


}