package com.example.example2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View
import android.content.SharedPreferences
import android.content.Context

class Welcome : AppCompatActivity() {
    private val PREFS_NAME = "MyPrefsFile"
    private val KEY_FIRST_RUN = "firstRun"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
    }
    fun buttonClick(view:View?)
    {
        val sharedPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPrefs.edit()
        editor.putBoolean(KEY_FIRST_RUN, false)
        editor.apply()
        finish()
    }
}