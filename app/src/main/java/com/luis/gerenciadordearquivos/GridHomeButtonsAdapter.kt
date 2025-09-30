package com.luis.gerenciadordearquivos

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getColor
import com.luis.gerenciadordearquivos.models.HomeButtonViewModel

class GridHomeButtonsAdapter(context: Context?,  buttons : ArrayList<HomeButtonViewModel?>)
    : BaseAdapter() {

    private var context: Context? = null
    private var buttons: ArrayList<HomeButtonViewModel?> = ArrayList()

    init {
        this.context = context
        this.buttons = buttons
    }

    private var inflater: LayoutInflater? = null

    override fun getCount(): Int {
        return buttons.size
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
            newConvertView = inflater?.inflate(R.layout.button_item, null)

        val imgButton = newConvertView?.findViewById<ImageView>(R.id.img_button)
        val txtButton = newConvertView?.findViewById<TextView>(R.id.txt_button)

        val button = buttons[position]

        imgButton?.setImageResource(button!!.drawable)
        txtButton?.text = button?.name

        return newConvertView
    }
}