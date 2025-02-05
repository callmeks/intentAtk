package io.ks.intentatk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class RecvIntent20: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (intent.action == "io.hextree.broadcast.GET_FLAG") {
            val intentDetails = Utils.dumpIntent(context, intent)
            Log.d("IntentDump", intentDetails)

            val test = Intent().apply{
                setPackage("io.hextree.attacksurface")
                action = "io.hextree.broadcast.GET_FLAG"
                putExtra("give-flag",true)
            }
            context.sendBroadcast(test)
        }
    }
}