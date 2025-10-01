package com.luis.gerenciadordearquivos

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast

open class ReadAndWriteMedia() : CheckPermissionsForApp() {
    private var myRequestCode = -1

    protected fun readFile(uri: Uri) {
        val resolver = applicationContext.contentResolver

        val readOnlyMode = "r"
        resolver.openFileDescriptor(uri, readOnlyMode).use { pdf ->
            Log.v("PDF TEST", pdf.toString())
            pdf?.describeContents()
        }
    }

    protected fun readImage() {
        
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == myRequestCode && resultCode == RESULT_OK) {
            Log.v("Opening file", "Open")
        }
    }

    protected fun getTheLocalStorageInfo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val volumesNames : Set<String> = MediaStore.getExternalVolumeNames(this)
            val firstVolumeName = volumesNames.iterator().next()
            Log.v("LocalStorageInfo", volumesNames.toString())
            Log.v("LocalStorageInfo", firstVolumeName)
        }
    }
}