package com.luis.gerenciadordearquivos.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.luis.gerenciadordearquivos.DecodeResAmpler
import com.luis.gerenciadordearquivos.GridListFilesAdapter
import com.luis.gerenciadordearquivos.IOnBackPressed
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.RequestFileCode
import com.luis.gerenciadordearquivos.activitys.HomeActivity
import com.luis.gerenciadordearquivos.models.FileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class LocalStorageFragment : Fragment(), IOnBackPressed {

    private lateinit var txtPath : TextView
    private lateinit var listFileGridView : GridView

    private var storageDirectory : File = File("")
    private var currentFile : File = File("")
    private var listFileViewModel : ArrayList<FileViewModel?> = ArrayList()

    private val tag = javaClass.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_local_storage, container, false)

        txtPath = view.findViewById(R.id.txt_path)
        listFileGridView = view.findViewById(R.id.grid_file)

        storageDirectory = Environment.getExternalStorageDirectory()
        currentFile = storageDirectory

        Log.v(tag, currentFile.toString())

        return view
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
        val fileSelected = listFileViewModel[position]

        if (fileSelected == null)
            return

        if (fileSelected.file.isDirectory)
            goingToTheNextFileDirectory(fileSelected.file)

        if (fileSelected.goBack)
            backToTheLastFileDirectory()

        if (fileSelected.name.toString().endsWith(".pdf")) {
            val uriPdf = FileProvider.getUriForFile(context,
                context?.applicationContext?.packageName + ".provider",
                fileSelected.file)
            (context as? HomeActivity)?.openPdfFile(uriPdf)
        }

    }

    private fun setTheGridViewOfCurrentFiles() {
        val gridListFilesAdapter = GridListFilesAdapter(context, listFileViewModel)
        listFileGridView.adapter = gridListFilesAdapter

        listFileGridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            onClickListenerItemInGridViewFiles(position)
        }
    }

    private fun setCurrentListFiles() {
        txtPath.text = currentFile.toString()

        listFileViewModel.clear()

        val files : ArrayList<FileViewModel?> = ArrayList<FileViewModel?>()

        currentFile.listFiles()?.forEach { it ->
            var fileViewModel = FileViewModel()
            fileViewModel.file = it
            fileViewModel.name = it.name
            files.add(fileViewModel)
        }

        listFileViewModel = files

        setTheGridViewOfCurrentFiles()
    }
    override fun onStart() {
        super.onStart()
        setCurrentListFiles()
    }

    override fun backPressed() {
        if (currentFile == storageDirectory)
            fragmentManager?.beginTransaction()?.replace(R.id.main_container, HomeFragment())?.commit()

        backToTheLastFileDirectory()
    }
}