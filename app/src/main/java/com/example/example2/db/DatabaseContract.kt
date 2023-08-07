package com.example.example2.db

import android.provider.BaseColumns

object ExpensesContract: BaseColumns {
    object ExpensesEntry : BaseColumns {
        const val TABLE_NAME = "expenses"
        const val COLUMN_EXPENSE_VALUE = "expense_value"
        const val COLUMN_EXPENSE_DATE = "expense_date"
        const val COLUMN_EXPENSE_CATEGORY = "expense_category"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ExpensesEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ExpensesEntry.COLUMN_EXPENSE_VALUE} BigDecimal," +
                "${ExpensesEntry.COLUMN_EXPENSE_DATE} DATE, " +
                "${ExpensesEntry.COLUMN_EXPENSE_CATEGORY} INTEGER, "+
                "FOREIGN KEY(${ExpensesEntry.COLUMN_EXPENSE_CATEGORY}) " +
                "REFERENCES ${ExpenseCategoriesContract.ExpenseCategoriesEntry.TABLE_NAME}(${BaseColumns._ID}))"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ExpensesEntry.TABLE_NAME}"

}

object ExpenseCategoriesContract: BaseColumns {
    object ExpenseCategoriesEntry : BaseColumns {
        const val TABLE_NAME = "expense_categories"
        const val COLUMN_EXPENSE_CATEGORY_NAME = "expense_category_name"
        const val COLUMN_EXPENSE_CATEGORY_IMAGE_ID = "expense_category_image_id"
        const val COLUMN_EXPENSE_CATEGORY_COLOR = "expense_category_color"
    }

    const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${ExpenseCategoriesEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_IMAGE_ID} INTEGER," +
                "${ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_NAME} TEXT," +
                "${ExpenseCategoriesEntry.COLUMN_EXPENSE_CATEGORY_COLOR} INTEGER)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${ExpenseCategoriesEntry.TABLE_NAME}"

}