package com.luis.gerenciadordearquivos.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import com.luis.gerenciadordearquivos.GridListFilesAdapter
import com.luis.gerenciadordearquivos.IOnBackPressed
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.models.FileViewModel
import java.io.File

class LocalStorageFragment : Fragment(), IOnBackPressed {

    private lateinit var txtPath : TextView
    private lateinit var listFileGridView : GridView

    private var storageDirectory : File = File("")
    private var currentFile : File = File("")
    private var listFileViewModel : ArrayList<FileViewModel?> = ArrayList()

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
        val fileSelected = listFileViewModel[position]!!

        if (fileSelected.goBack) {
            backToTheLastFileDirectory()
        }

        if (fileSelected.file.isDirectory) {
            goingToTheNextFileDirectory(fileSelected.file)
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

    override fun backPressed() {
        if (currentFile == storageDirectory)
            fragmentManager?.beginTransaction()?.replace(R.id.main_container, HomeFragment())?.commit()

        backToTheLastFileDirectory()
    }
}