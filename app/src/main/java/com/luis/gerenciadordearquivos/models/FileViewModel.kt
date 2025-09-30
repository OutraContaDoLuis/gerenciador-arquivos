package com.luis.gerenciadordearquivos.models

import android.graphics.Bitmap
import android.net.Uri
import java.io.File
import java.io.Serializable

data class FileViewModel (
    var name: String = "",
    var file: File = File(""),
    var itsBitmap: Boolean = false,
    var bitmap: Bitmap? = null,
    var uri: Uri? = null,
    var drawableInt: Int = 0,
    var goBack: Boolean = false
) : Serializable