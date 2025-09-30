package com.luis.gerenciadordearquivos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import com.luis.gerenciadordearquivos.models.FileViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sqrt

class GridListFilesAdapter(context: Context?, files: ArrayList<File>) : BaseAdapter() {

    private var context: Context? = null
    private var files: ArrayList<File> = ArrayList<File>()

    init {
        this.context = context
        this.files = files
    }

    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return if (files.isNotEmpty()) {
            files.size
        } else {
            0
        }
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    @SuppressLint("InflateParams", "UseKtx")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (inflater == null)
            inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

        var newConvertView = convertView

        if (newConvertView == null)
            newConvertView = inflater?.inflate(R.layout.file_item, null)

        val imgFile = newConvertView?.findViewById<ImageView>(R.id.img_button)
        val txtFile = newConvertView?.findViewById<TextView>(R.id.txt_button)

        val currentFile = files[position]

        if (currentFile.isDirectory) {
            imgFile?.setImageResource(R.drawable.folder)
        } else {
            if (currentFile.isFile &&
                (currentFile.name.endsWith(".png")
                || currentFile.name.endsWith(".jpg")
                || currentFile.name.endsWith(".jpeg"))) {
                imgFile?.setImageResource(R.drawable.image_asset)

                CoroutineScope(Dispatchers.Main).launch {
                    val image = async {
                        getImageBitmap(currentFile, currentFile.toUri())
                    }

                    imgFile?.setImageBitmap(image.await())
                }
            }
        }

        txtFile?.text = currentFile.name

        return newConvertView
    }

    private fun getImageBitmap(file: File, uri: Uri?) : Bitmap {
        val source = ImageDecoder.createSource(context?.contentResolver!!, uri!!)
        val bitmap = ImageDecoder.decodeBitmap(source)
        val fileOutStream = FileOutputStream(file)
        val resizeBitmap = resizeToTargetSize(bitmap)
        resizeBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutStream)
        fileOutStream.flush()
        fileOutStream.close()
        return resizeBitmap
    }

    @SuppressLint("UseKtx")
    private fun resizeToTargetSize(bitmap: Bitmap, targetSizeKB: Int = 128): Bitmap {
        val targetBytes = targetSizeKB * 128

        val maxDimension = sqrt((targetBytes * 0.7).toDouble()).toInt()

        var currentBitmap = bitmap
        if (bitmap.width > maxDimension || bitmap.height > maxDimension) {
            val ratio =
                minOf(maxDimension.toFloat() / bitmap.width, maxDimension.toFloat() / bitmap.height)
            val newWidth = (bitmap.width * ratio).toInt()
            val newHeight = (bitmap.height * ratio).toInt()
            currentBitmap = Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
        }

        return currentBitmap
    }

}