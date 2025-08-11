package com.luis.gerenciadordearquivos

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.luis.gerenciadordearquivos.models.FileViewModel
import java.io.File

class GridListFilesAdapter(context: Context?, files: ArrayList<FileViewModel?>) : BaseAdapter() {

    private var context: Context? = null
    private var files: ArrayList<FileViewModel?> = ArrayList()

    init {
        this.context = context
        this.files = files
    }

    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return files.size
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (inflater == null)
            inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?

        var newConvertView = convertView

        if (newConvertView == null)
            newConvertView = inflater?.inflate(R.layout.file_item, null)

        val imgFile = newConvertView?.findViewById<ImageView>(R.id.img_file)
        val txtFile = newConvertView?.findViewById<TextView>(R.id.txt_file)

        val file = files[position]

        if (file!!.goBack) {
            imgFile?.setImageResource(R.drawable.folder)
        } else if (file.file.isDirectory) {
            imgFile?.setImageResource(R.drawable.folder)
        } else if (file.file.isFile) {
            if (file.name.endsWith(".pdf")) {
                imgFile?.setImageResource(R.drawable.pdf)
            } else if (file.name.endsWith(".jpeg")
                || file.name.endsWith(".png")
                || file.name.endsWith(".jpg")) {
                imgFile?.setImageResource(R.drawable.image_asset)
            }
        }

        txtFile?.text = file.name

        return newConvertView
    }
}