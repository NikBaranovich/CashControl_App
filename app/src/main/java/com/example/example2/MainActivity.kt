package com.example.example2

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.example2.databinding.ActivityMainBinding
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
            // Показать форму или выполнить необходимые действия для первого запуска
            val welcomeIntent = Intent(this@MainActivity, Welcome::class.java)
            startActivity(welcomeIntent)
            // После выполнения действий для первого запуска, установить флаг в false
            val editor: SharedPreferences.Editor = sharedPrefs.edit()
            editor.putBoolean(KEY_FIRST_RUN, false)
            editor.apply()
        }
        setContentView(R.layout.activity_main)
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

    }
}