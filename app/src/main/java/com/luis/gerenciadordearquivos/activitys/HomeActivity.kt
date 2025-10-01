package com.luis.gerenciadordearquivos.activitys

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.luis.gerenciadordearquivos.BaseActivity
import com.luis.gerenciadordearquivos.IOnBackPressed
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.RequestFileCode
import kotlin.math.roundToInt

class HomeActivity : BaseActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var btnPopupMenu : FloatingActionButton

    private val tag = javaClass.name

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        btnPopupMenu = findViewById(R.id.btn_popup_menu)

        btnPopupMenu.setOnClickListener { it ->
            val popupMenu = PopupMenu(this@HomeActivity, it)
            popupMenu.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.create_new_file_directory -> {
                        true
                    }
                    else -> false
                }
            }

            try {
                val fieldPopup = PopupMenu::class.java.getDeclaredField("mPopup")
                fieldPopup.isAccessible = true
                val mPopup = fieldPopup.get(popupMenu)
                mPopup.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mPopup, true)
            } catch (e: Exception) {
                Log.e(tag, "Error showing menu icons.", e)
            }

            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.show()
        }
    }

    override fun onStart() {
        super.onStart()
    }

    private fun transitionToFragment(fragment : Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }

    private fun onBackPressedFragmentEvents() : Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)

        (fragment as? IOnBackPressed)?.backPressed()?.let {
            return false
        }

        return true
    }

    override fun onBackPressed() {
        val callSuperOnBackPressed = onBackPressedFragmentEvents()

        if (callSuperOnBackPressed) {
            super.onBackPressed()
        }
    }

    fun openPdfFile(uri : Uri?) {
        val target = Intent(Intent.ACTION_VIEW)
        target.setDataAndType(uri, "application/pdf")
        target.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        val intent = Intent.createChooser(target, "Open PDF File")
        try {
            startActivityForResult(intent, 2)
        } catch (e: ActivityNotFoundException) {
            Log.e(tag, "Activity not founded", e)
        }
    }

}