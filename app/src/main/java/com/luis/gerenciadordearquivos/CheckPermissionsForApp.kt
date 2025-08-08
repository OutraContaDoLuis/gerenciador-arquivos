package com.luis.gerenciadordearquivos

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

open class CheckPermissionsForApp() : AppCompatActivity() {

    private val requestPermissionCodeWriteStorage = 1

    protected fun hasPermissionToManageStorage() : Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            if (ActivityCompat
                .checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
                false
            }
            true
        }
    }

    protected val storageActivityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (hasPermissionToManageStorage()) {
                    Toast.makeText(this, "Permission ok!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show()
                }
            }
        }

    protected fun requestExternalStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                storageActivityResultLauncher.launch(intent)
            } catch (ex: Exception) {
                Log.v("ERROR PERMISSION", ex.message.toString())
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                storageActivityResultLauncher.launch(intent)
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                requestPermissionCodeWriteStorage
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (requestCode == requestPermissionCodeWriteStorage) {
            if (hasPermissionToManageStorage()) {
                Toast.makeText(this, "Permission ok!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_LONG).show()
            }
        }
    }
}