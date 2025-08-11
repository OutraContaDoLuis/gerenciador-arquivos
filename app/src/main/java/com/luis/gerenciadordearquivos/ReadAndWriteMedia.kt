package com.luis.gerenciadordearquivos

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast

open class ReadAndWriteMedia() : CheckPermissionsForApp() {
    private var myRequestCode = -1

    protected fun openFile(pickInitialUri: Uri, requestFileCode: RequestFileCode) {
        if (hasPermissionToManageStorage()) {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "application/pdf"

                putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickInitialUri)
            }

            myRequestCode = when (requestFileCode) {
                RequestFileCode.PICK_PDF_FILE -> 2
                RequestFileCode.CREATE_FILE -> 1
            }

            startActivityForResult(intent, myRequestCode)
        } else {
            Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show()
        }
    }

    protected fun readFile(uri: Uri) {
        val resolver = applicationContext.contentResolver

        val readOnlyMode = "r"
        resolver.openFileDescriptor(uri, readOnlyMode).use { pdf ->
            Log.v("PDF TEST", pdf.toString())
            pdf?.describeContents()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == myRequestCode && resultCode == RESULT_OK) {
            Log.v("Opening file", "Open")
        }
    }
}