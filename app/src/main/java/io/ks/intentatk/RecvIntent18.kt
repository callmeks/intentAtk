package io.ks.intentatk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class RecvIntent18 : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if(intent.action == "io.hextree.broadcast.FREE_FLAG"){
            val flag = intent.getStringExtra("flag")

            Log.d("test", flag!!)
            resultData = "gay"
            resultCode = 1
        }


    }

}