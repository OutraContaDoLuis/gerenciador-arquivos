package com.luis.gerenciadordearquivos

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.luis.gerenciadordearquivos.models.FileViewModel
import java.io.File

class HomeActivity : BaseActivity() {

    private lateinit var gridFiles: GridView
    private var gridListFilesAdapter: GridListFilesAdapter? = null

    private var storageDirectory : File? = null
    private var currentFile: File? = null
    private var currentListFileViewModel : ArrayList<FileViewModel?>? = ArrayList()
    private var fileHistory: ArrayList<File?>? = ArrayList()

    private var tag : String = tagName()

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
        currentFile = storageDirectory
        fileHistory?.add(currentFile)
        Log.v(tag, "Root directory: ${storageDirectory.toString()}")
        Log.v(tag, "Files in root directory: ${storageDirectory?.listFiles()}")
    }

    private fun onClickListenerItemInGridViewFiles(position: Int) {
        if (currentListFileViewModel != null && fileHistory != null) {
            if (currentListFileViewModel!![position]?.file!!.isDirectory) {
                currentFile = if (currentListFileViewModel!![position]!!.goBack) {
                    fileHistory!!.removeAt(fileHistory!!.size - 1)
                    fileHistory!![fileHistory!!.size - 1]
                } else {
                    fileHistory?.add(currentListFileViewModel!![position]?.file)
                    currentListFileViewModel!![position]?.file
                }
            } else if (currentListFileViewModel!![position]?.file!!.isFile) {

            }
        }

        Log.v(tag, fileHistory.toString())

        currentFile?.listFiles()?.forEach { it ->
            Log.v(tag, it.toString())
        }
    }

    private fun setTheGridViewOfCurrentFiles() {
        gridListFilesAdapter = GridListFilesAdapter(this, currentListFileViewModel)
        gridFiles.adapter = gridListFilesAdapter

        gridFiles.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            onClickListenerItemInGridViewFiles(position)
            setCurrentArrayListFileViewModel()
        }
    }

    private fun setCurrentArrayListFileViewModel() {
        currentListFileViewModel = ArrayList()
        if (currentFile != storageDirectory) {
            val fileViewModelGoBack = FileViewModel()
            fileViewModelGoBack.name = "..."
            fileViewModelGoBack.goBack = true

            currentListFileViewModel?.add(fileViewModelGoBack)
        }

        currentFile?.listFiles()?.forEach {it ->
            var fileViewModel = FileViewModel()
            fileViewModel.name = it.name
            fileViewModel.file = it
            currentListFileViewModel?.add(fileViewModel)
        }

        setTheGridViewOfCurrentFiles()
    }

    override fun onStart() {
        super.onStart()
        setCurrentArrayListFileViewModel()
    }
}