package io.ks.intentatk

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class RecvIntent23 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the incoming intent with the action
        val action1 = intent?.action
        if (action1 == "io.hextree.attacksurface.MUTATE_ME") {
            val pendingIntent = intent?.extras?.getParcelable("pending_intent", PendingIntent::class.java)
            if (pendingIntent != null) {
                // Create an Intent to carry the result
                val resultIntent = Intent().apply {
                    putExtra("code", 42) // Example: Adding result code
                }
                // Send the result back using the PendingIntent
                pendingIntent.send(this, 0, resultIntent)
            }
        }
        // Finish the activity after setting the result
        finish()
    }

}
