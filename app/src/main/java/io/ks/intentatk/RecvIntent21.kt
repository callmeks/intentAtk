package io.ks.intentatk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class RecvIntent21: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "io.hextree.broadcast.GIVE_FLAG") {
            val intentDetails = Utils.dumpIntent(context, intent)
            Log.d("IntentDump", intentDetails)
        }
    }
}