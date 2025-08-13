package com.luis.gerenciadordearquivos.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luis.gerenciadordearquivos.BaseActivity
import com.luis.gerenciadordearquivos.GridListFilesAdapter
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.models.FileViewModel
import java.io.File

class HomeActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var btnCardLocalStorage : CardView

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
        btnCardLocalStorage = findViewById(R.id.btn_card_local_storage)
    }

    private fun goToFileExplorerActivity() {
        val intent = Intent(this, LocalStorageActivity::class.java)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        btnCardLocalStorage.setOnClickListener { goToFileExplorerActivity() }
    }
}