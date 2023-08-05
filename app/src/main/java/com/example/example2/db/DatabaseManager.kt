package com.example.example2.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.example2.ExpenseCategoryData
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
//    fun insertExpense(expenseValue: BigDecimal, expenseDate: Date, expenseCategory: Int)
//    {
//        val values = ContentValues().apply {
//            put(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_VALUE, expenseValue.toString())
//            put(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_DATE, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(expenseDate))
//            put(ExpensesContract.ExpensesEntry.COLUMN_EXPENSE_CATEGORY, expenseCategory)
//        }
//        val newRowId = db?.insert(ExpensesContract.ExpensesEntry.TABLE_NAME, null, values)
//    }
    fun insertExpenseCategory(name: String, imageId: Int) {
        val values = ContentValues().apply {
            put(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME, name)
            put(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID, imageId)
        }
        val newRowId = db?.insert(ExpenseCategoriesContract.ExpenseCategoriesEntry.TABLE_NAME, null, values)
    }
    data class ExpenseData(val expenseValue: BigDecimal, val expenseDate: Date, val expenseCategory: Int)

    fun mapCursorToExpenseCategoryDataList(cursor: Cursor): List<ExpenseCategoryData> {
        val expenseCategories = mutableListOf<ExpenseCategoryData>()

        // Перемещаем указатель курсора на первую позицию
        if (cursor.moveToFirst()) {
            do {
                val imageId = cursor.getInt(cursor.getColumnIndexOrThrow(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME))

                val expenseCategoryData = ExpenseCategoryData(imageId, name)
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
            ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID,
            ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME
        )

        val sortOrder = "${ExpenseCategoriesContract.ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME} ASC"

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
}