package com.luis.gerenciadordearquivos.models

import java.io.File
import java.io.Serializable

data class FileViewModel (
    var name: String = "",
    var file: File = File(""),
    var goBack: Boolean = false
) : Serializable