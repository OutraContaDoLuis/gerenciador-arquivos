package com.luis.gerenciadordearquivos.models

import java.io.File

data class FileViewModel (
    var name: String? = null,
    var file: File? = null,
    var goBack: Boolean = false
)