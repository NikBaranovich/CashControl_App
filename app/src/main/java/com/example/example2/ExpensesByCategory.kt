package com.example.example2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.example2.databinding.ActivityExpensesByCategoryBinding
import com.example.example2.db.DatabaseManager

class ExpensesByCategory : AppCompatActivity() {
    lateinit var binding: ActivityExpensesByCategoryBinding
    private val adapter = ExpensesByCategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpensesByCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }
    private fun init(){
        val databaseManager = DatabaseManager(this)
        binding.apply{
            recyclerExpenses.layoutManager = LinearLayoutManager(this@ExpensesByCategory)
            recyclerExpenses.adapter = adapter
            databaseManager.openDb()
            val categoryId = intent.getIntExtra("categoryId", -1)
            val expenses = databaseManager.getExpensesByCategory(categoryId)
            adapter.updateExpenses(expenses)
        }
    }
}