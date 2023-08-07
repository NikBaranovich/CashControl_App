package com.example.example2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.GridLayout
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.example2.db.DatabaseManager
import java.util.Date

class ExpensesAdd : AppCompatActivity() {
    private lateinit var expenseCategories: List<ExpenseCategoryData>
    private lateinit var categoryGridLayout: GridLayout
    private lateinit var expenseAmountEditText: EditText
    private var selectedCategory: ExpenseCategoryData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_add)
        categoryGridLayout = findViewById<GridLayout>(R.id.gridLayout)
        expenseAmountEditText = findViewById(R.id.expenseAmountEditText)
        val databaseManager = DatabaseManager(this)
        databaseManager.openDb()
        expenseCategories = databaseManager.getAllExpenseCategories()
        createCategoryButtons()
    }
    private fun createCategoryButtons() {
        categoryGridLayout.columnCount = 4
        for (category in expenseCategories) {
            val button = ImageButton(this)
            button.setImageResource(category.imageId)
            button.setOnClickListener {
                selectedCategory = category
            }

            val buttonLayoutParams = GridLayout.LayoutParams().apply {
                width = GridLayout.LayoutParams.WRAP_CONTENT
                height = GridLayout.LayoutParams.WRAP_CONTENT
                setMargins(16, 16, 16, 16)
            }
            button.layoutParams = buttonLayoutParams

            val textView = TextView(this).apply {
                text = category.name
                maxLines = 1
                isSingleLine = true

                ellipsize = TextUtils.TruncateAt.END
                setTextColor(ContextCompat.getColor(this@ExpensesAdd, android.R.color.black))
            }

            val textLayoutParams = GridLayout.LayoutParams().apply {
                width = GridLayout.LayoutParams.WRAP_CONTENT
                height = GridLayout.LayoutParams.WRAP_CONTENT
                setMargins(0, 8, 0, 0)
            }
            textView.layoutParams = textLayoutParams

            val categoryLayout = GridLayout(this).apply {
                columnCount = 1
                addView(button)
                addView(textView)
            }

            val categoryLayoutParams = GridLayout.LayoutParams().apply {
                width = 200
                height = GridLayout.LayoutParams.WRAP_CONTENT
            }
            categoryLayout.layoutParams = categoryLayoutParams

            categoryGridLayout.addView(categoryLayout)
        }
    }
    fun saveExpense(view: View?) {
        val expenseValue = expenseAmountEditText.text.toString().toBigDecimalOrNull()
        val expenseDate = Date()
        if (expenseValue != null && selectedCategory != null) {
            val databaseManager = DatabaseManager(this)
            databaseManager.openDb()
            databaseManager.insertExpense(expenseValue, expenseDate, selectedCategory!!.id!!)
            Toast.makeText(applicationContext, "Сумма: $expenseValue\nКатегория: ${selectedCategory!!.name}", Toast.LENGTH_LONG).show()
            val expenses = databaseManager.getAllExpenses()
            finish()
        }
    }
    fun menuButtonClick(view:View?) {
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.openDrawer(GravityCompat.START)
    }

}