package com.luis.gerenciadordearquivos.fragments

import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luis.gerenciadordearquivos.R
import java.io.File

class ImageStorageFragment : Fragment() {

    private var storageDirectory : File = File("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}

        storageDirectory = Environment.getExternalStorageDirectory()

        val getFilesFromPictureDirectory = File("$storageDirectory/Picture")
        val getFilesFromDCMIDirectory = File("$storageDirectory/DCIM")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_storage, container, false)

        return view
    }
}