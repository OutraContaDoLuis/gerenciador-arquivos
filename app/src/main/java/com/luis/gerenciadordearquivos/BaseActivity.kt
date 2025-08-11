package com.luis.gerenciadordearquivos

open class BaseActivity() : ReadAndWriteMedia() {

    protected fun tagName() : String {
        return javaClass.name
    }

}