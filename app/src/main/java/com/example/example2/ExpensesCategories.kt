package com.example.example2

import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.GridLayout
import android.widget.GridView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.example2.databinding.ActivityExpensesCategoriesBinding
import com.example.example2.db.DatabaseManager

class ExpensesCategories : AppCompatActivity() {
    lateinit var binding: ActivityExpensesCategoriesBinding
    private val adapter = ExpenseCategoryAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpensesCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }
    private fun init(){
        val databaseManager = DatabaseManager(this)
        binding.apply{
            recyclerCategories.layoutManager = GridLayoutManager(this@ExpensesCategories, 3)
            recyclerCategories.adapter = adapter
            databaseManager.openDb()
            val expenseCategories = databaseManager.getAllExpenseCategories()
            adapter.updateCategories(expenseCategories)
        }
    }
}