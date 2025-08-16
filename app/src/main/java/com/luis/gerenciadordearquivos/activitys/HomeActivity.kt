package com.luis.gerenciadordearquivos.activitys

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.luis.gerenciadordearquivos.BaseActivity
import com.luis.gerenciadordearquivos.IOnBackPressed
import com.luis.gerenciadordearquivos.R
import com.luis.gerenciadordearquivos.fragments.HomeFragment
import com.luis.gerenciadordearquivos.fragments.ImageStorageFragment
import com.luis.gerenciadordearquivos.fragments.LocalStorageFragment

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar : Toolbar
    private lateinit var drawerLayout : DrawerLayout

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

        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
            R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {

            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun onBackPressedFragmentEvents() : Boolean {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)

        (fragment as? IOnBackPressed)?.backPressed()?.let {
            return false
        }

        return true
    }

    override fun onBackPressed() {
        val callSuperOnBackPressed = onBackPressedFragmentEvents()

        if (callSuperOnBackPressed) {
            super.onBackPressed()
        }
    }
}