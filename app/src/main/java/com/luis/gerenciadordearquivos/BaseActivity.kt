package com.luis.gerenciadordearquivos

open class BaseActivity() : CheckPermissionsForApp() {

    protected fun tagName() : String {
        return javaClass.name
    }

}