package com.example.example2

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.example2.db.DatabaseManager
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle : ActionBarDrawerToggle
    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_FIRST_RUN = "firstRun"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val isFirstRun = sharedPrefs.getBoolean(KEY_FIRST_RUN, true)

        if (isFirstRun) {
            val welcomeIntent = Intent(this@MainActivity, Welcome::class.java)
            startActivity(welcomeIntent)
        }
        setContentView(R.layout.activity_main)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayShowHomeEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId)
            {
                R.id.nav_expenses -> {
                    val intent = Intent(this, ExpensesCategories::class.java)
                    startActivity(intent)
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
        if(toggle.onOptionsItemSelected(item))
            return true
    }
    fun menuButtonClick(view:View?) {
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.openDrawer(GravityCompat.START)
    }

    fun expancesButtonClick(view:View?) {
        val intent = Intent(this, ExpensesAdd::class.java)
        startActivity(intent)
    }
    fun incomeButtonClick(view:View?) {
    }
}