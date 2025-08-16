package com.luis.gerenciadordearquivos.activitys

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.luis.gerenciadordearquivos.BaseActivity
import com.luis.gerenciadordearquivos.GridHomeButtonsAdapter
import com.luis.gerenciadordearquivos.GridListFilesAdapter
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.models.FileViewModel
import com.luis.gerenciadordearquivos.models.HomeButtonViewModel
import java.io.File

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar : Toolbar
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var btnCardLocalStorage : CardView
    private lateinit var gridViewButtons : GridView

    private var listOfHomeButtonViewModel : ArrayList<HomeButtonViewModel?> = ArrayList()
    private val listOfNameHomeButtonViewModel : ArrayList<String> = arrayListOf(
        "Imagens", "Videos", "Arquivos", "Audio", "Apps"
    )
    private val listOfDrawableHomeButtonViewModel : ArrayList<Int> = arrayListOf(
        R.drawable.ic_baseline_image_24, R.drawable.ic_baseline_video_file_24,
        R.drawable.ic_baseline_insert_drive_file_24, R.drawable.ic_baseline_audio_file_24,
        R.drawable.ic_baseline_apps_24
    )
    private val listOfOnClickHomeButtonViewModel : ArrayList<AppCompatActivity> = arrayListOf(
        ImageStorageActivity(), LocalStorageActivity(), LocalStorageActivity(),
        LocalStorageActivity(), LocalStorageActivity()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
            R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
//        drawerLayout = findViewById(R.id.drawer_layout)
//        btnCardLocalStorage = findViewById(R.id.btn_card_local_storage)
//        gridViewButtons = findViewById(R.id.grid_buttons)
    }

//    private fun goToActivity(activity : AppCompatActivity) {
//        val intent = Intent(this, activity::class.java)
//        startActivity(intent)
//    }
//
//    private fun setTheArrayListOfHomeButtonViewModel() {
//        var size = listOfNameHomeButtonViewModel.size - 1
//        while (size >= 0) {
//            val homeButtonViewModel = HomeButtonViewModel()
//            homeButtonViewModel.name = listOfNameHomeButtonViewModel[size].toString()
//            homeButtonViewModel.drawable = listOfDrawableHomeButtonViewModel[size]
//            listOfHomeButtonViewModel.add(homeButtonViewModel)
//            size = size - 1
//        }
//        listOfHomeButtonViewModel =
//            listOfHomeButtonViewModel.reversed() as ArrayList<HomeButtonViewModel?>
//    }
//
//    private fun onClickListenerItemInGridViewButtons(position : Int) {
//        val activitySelection = listOfOnClickHomeButtonViewModel[position]
//        goToActivity(activitySelection)
//    }
//
//    private fun setTheAdapterForGridViewButtons() {
//        setTheArrayListOfHomeButtonViewModel()
//
//        val gridHomeButtonsAdapter = GridHomeButtonsAdapter(this, listOfHomeButtonViewModel)
//        gridViewButtons.adapter = gridHomeButtonsAdapter
//
//        gridViewButtons.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            onClickListenerItemInGridViewButtons(position)
//        }
//    }

    override fun onStart() {
        super.onStart()
//        btnCardLocalStorage.setOnClickListener { goToActivity(LocalStorageActivity()) }
////
////        if (listOfHomeButtonViewModel.size != listOfNameHomeButtonViewModel.size)
////            setTheAdapterForGridViewButtons()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}