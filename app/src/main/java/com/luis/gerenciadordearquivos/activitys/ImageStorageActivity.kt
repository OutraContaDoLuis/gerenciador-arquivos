package com.luis.gerenciadordearquivos.activitys

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luis.gerenciadordearquivos.BaseActivity
import com.luis.gerenciadordearquivos.R

class ImageStorageActivity : BaseActivity() {

    private lateinit var toolbar : Toolbar
    private lateinit var btnLoadImage : Button
    private lateinit var gridImages : GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_image_storage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        btnLoadImage = findViewById(R.id.btn_add_image)
        gridImages = findViewById(R.id.grid_images)
    }

    override fun onStart() {
        super.onStart()
    }
}