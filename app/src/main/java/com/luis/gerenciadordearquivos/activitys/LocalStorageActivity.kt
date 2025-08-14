package com.luis.gerenciadordearquivos.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luis.gerenciadordearquivos.BaseActivity
import com.luis.gerenciadordearquivos.GridListFilesAdapter
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.models.FileViewModel
import java.io.File

class LocalStorageActivity : BaseActivity() {

    private var textExtra : String = ""

    private lateinit var txtPath : TextView
    private lateinit var listFileGridView : GridView

    private var storageDirectory : File = File("")
    private var currentFile : File = File("")
    private var listFileViewModel : ArrayList<FileViewModel?> = ArrayList()

    private var tag : String = tagName()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_local_storage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtPath = findViewById(R.id.txt_path)
        listFileGridView = findViewById(R.id.grid_files)

        storageDirectory = Environment.getExternalStorageDirectory()
        currentFile = storageDirectory

        Log.v(tag, "Root directory: $storageDirectory")
        Log.v(tag, "Files in root directory: ${storageDirectory.listFiles()}")
    }

    private fun backToTheLastFileDirectory() {
        val intRange =
            ((currentFile.toString().length - 1) - currentFile.name.toString().length)..
                    (currentFile.toString().length - 1)
        val namePathWithoutFileOfCurrentFile = currentFile.toString().removeRange(intRange)
        Log.v(tag, namePathWithoutFileOfCurrentFile)
        currentFile = File(namePathWithoutFileOfCurrentFile)
        setCurrentListFiles()
    }

    private fun goingToTheNextFileDirectory(file: File) {
        currentFile = file
        setCurrentListFiles()
    }

    private fun onClickListenerItemInGridViewFiles(position: Int) {
        val fileSelected = listFileViewModel[position]!!

        if (fileSelected.goBack) {
            backToTheLastFileDirectory()
        }

        if (fileSelected.file.isDirectory) {
            goingToTheNextFileDirectory(fileSelected.file)
        }
    }

    private fun setTheGridViewOfCurrentFiles() {
        val gridListFilesAdapter = GridListFilesAdapter(this, listFileViewModel)
        listFileGridView.adapter = gridListFilesAdapter

        listFileGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            onClickListenerItemInGridViewFiles(position)
        }
    }

    private fun setCurrentListFiles() {
        txtPath.text = currentFile.toString()

        listFileViewModel = ArrayList()
        if (currentFile != storageDirectory) {
            val goBackFileViewModel = FileViewModel()
            goBackFileViewModel.name = "..."
            goBackFileViewModel.file = File("")
            goBackFileViewModel.goBack = true
            listFileViewModel.add(goBackFileViewModel)
        }

        currentFile.listFiles()?.forEach { it ->
            val fileViewModel = FileViewModel()
            fileViewModel.name = it.name
            fileViewModel.file = it
            fileViewModel.goBack = false
            listFileViewModel.add(fileViewModel)
        }

        setTheGridViewOfCurrentFiles()
    }

    override fun onStart() {
        super.onStart()
        setCurrentListFiles()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (currentFile != storageDirectory) {
            backToTheLastFileDirectory()
            return
        }

        super.onBackPressed()
    }
}