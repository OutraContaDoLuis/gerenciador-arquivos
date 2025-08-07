package com.luis.gerenciadordearquivos

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.GridView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class HomeActivity : AppCompatActivity() {

    private lateinit var gridFiles: GridView
    private var gridListFilesAdapter: GridListFilesAdapter? = null

    private var storageDirectory : File? = null

    private val tag = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        gridFiles = findViewById(R.id.grid_files)

        storageDirectory = Environment.getExternalStorageDirectory()
        Log.v(tag, "Root directory: ${storageDirectory.toString()}")
        Log.v(tag, "Files in root directory: ${storageDirectory?.listFiles()}")
    }

    private fun getAllTheDirectoriesFromRootDirectory() {
        val listOfFileFromStorageDirectory = storageDirectory?.listFiles()
        listOfFileFromStorageDirectory?.forEach { it ->
            Log.v(tag, it.toString())
        }

        gridListFilesAdapter = GridListFilesAdapter(this, listOfFileFromStorageDirectory)
        gridFiles.adapter = gridListFilesAdapter
    }

    override fun onStart() {
        super.onStart()
        getAllTheDirectoriesFromRootDirectory()
    }
}