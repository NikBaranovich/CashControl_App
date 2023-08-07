package com.example.example2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.example2.ExpenseCategoryData
import com.example.example2.ExpenseData
import com.example.example2.ExpensesCategories
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*

class DatabaseManager (val context : Context) {
    val DbHelper = DatabaseHelper(context)
    var db: SQLiteDatabase? = null
    fun openDb(){
        db = DbHelper.writableDatabase
    }
    fun insertExpense(expenseValue: BigDecimal, expenseDate: Date, expenseCategory: Int)
    {
        val values = ContentValues().apply {
            put(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_VALUE, expenseValue.toString())
            put(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_DATE, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(expenseDate))
            put(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_CATEGORY, expenseCategory)
        }
        val newRowId = db?.insert(ExpensesContract.ExpensesEntry.TABLE_NAME, null, values)
    }
    fun insertExpenseCategory(name: String, imageId: Int, color: Int) {
        val values = ContentValues().apply {
            put(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME, name)
            put(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID, imageId)
            put(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_COLOR, color)
        }
        val newRowId = db?.insert(ExpenseCategoriesContract.ExpenseCategoriesEntry.TABLE_NAME, null, values)
    }

    fun mapCursorToExpenseCategoryDataList(cursor: Cursor): List<ExpenseCategoryData> {
        val expenseCategories = mutableListOf<ExpenseCategoryData>()

        // Перемещаем указатель курсора на первую позицию
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val imageId = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME))
                val color = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_COLOR))
                val expenseCategoryData = ExpenseCategoryData(id, imageId, name, color)
                expenseCategories.add(expenseCategoryData)
            } while (cursor.moveToNext())
        }

        // Закрываем курсор
        cursor.close()

        return expenseCategories
    }
    fun getAllExpenseCategories(): List<ExpenseCategoryData> {
        val dbHelper = DatabaseHelper(context) // Ваш класс для работы с базой данных
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID,
            ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME,
            ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_COLOR
        )

        val sortOrder = "${BaseColumns._ID} ASC"

        val cursor = db.query(
            ExpenseCategoriesContract.ExpenseCategoriesEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        return mapCursorToExpenseCategoryDataList(cursor)
    }

    fun mapCursorToExpenseDataList(cursor: Cursor): List<ExpenseData> {
        val expenses = mutableListOf<ExpenseData>()

        // Перемещаем указатель курсора на первую позицию
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
                val value = cursor.getInt(cursor.getColumnIndexOrThrow(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_VALUE))
                val dateString = cursor.getString(cursor.getColumnIndexOrThrow(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_DATE))
                val dateFormat = SimpleDateFormat("yyyy-MM-dd") // Укажите здесь формат даты, который соответствует вашему формату в базе данных
                val date: Date = dateFormat.parse(dateString)
                val category = cursor.getInt(cursor.getColumnIndexOrThrow(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_CATEGORY))
                val expenseData = ExpenseData(id, value, date, category)
                expenses.add(expenseData)
            } while (cursor.moveToNext())
        }

        // Закрываем курсор
        cursor.close()

        return expenses
    }
    fun getAllExpenses(): List<ExpenseData> {
        val dbHelper = DatabaseHelper(context) // Ваш класс для работы с базой данных
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_VALUE,
            ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_DATE,
            ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_CATEGORY
        )

        val sortOrder = "${BaseColumns._ID} ASC"

        val cursor = db.query(
            ExpensesContract.ExpensesEntry.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            sortOrder
        )

        return mapCursorToExpenseDataList(cursor)
    }
}