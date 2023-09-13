package com.example.example2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.example2.colorpicker.ColorPicker
import com.example.example2.db.DatabaseManager
import yuku.ambilwarna.AmbilWarnaDialog

class ExpenseCategoryAdd : AppCompatActivity() {

    private lateinit var mLayout: ConstraintLayout
    private lateinit var mPickColorButton: Button
    private lateinit var mColorPreview: View
    private lateinit var mColor: ColorPicker
    private var mDefaultColor = Color.BLACK
    private lateinit var iconButtons: Array<ImageButton>
    private val adapter = ExpenseCategoryAdapter()
    private var selectedIcon: Int = R.drawable.baseline_circle_24

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense_category_add)
        mLayout = findViewById(R.id.layout)
        mColorPreview = findViewById(R.id.preview_selected_color)
        mColor = findViewById(R.id.colorPicker)
        mPickColorButton = findViewById(R.id.show_color_picker_button)
        mPickColorButton.setOnClickListener{
            openColorPicker()
        }
        iconButtons = arrayOf(
            findViewById(R.id.imageButton1),
            findViewById(R.id.imageButton2),
            findViewById(R.id.imageButton3),
            findViewById(R.id.imageButton4),
            findViewById(R.id.imageButton5),
            findViewById(R.id.imageButton6),
            findViewById(R.id.imageButton7),
            findViewById(R.id.imageButton8)
        )
        val icons = listOf(
            R.drawable.baseline_attach_money_24,
            R.drawable.baseline_menu_24,
            R.drawable.baseline_money_off_24,
            R.drawable.baseline_pie_chart_24,
            R.drawable.baseline_settings_24,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground
        )
        for (i in iconButtons.indices) {
            iconButtons[i].setOnClickListener {
                selectedIcon = icons[i]
                resetButtonBackground()

                iconButtons[i].isSelected = true
            }
        }
    }
    private fun resetButtonBackground() {
        for (button in iconButtons) {
            button.isSelected = false
        }
    }
    fun saveCategory(view:View?)
    {
        val categoryText: TextView = findViewById(R.id.categoryName)
        val databaseManager = DatabaseManager(this)
        databaseManager.openDb()
        databaseManager.insertExpenseCategory(categoryText.text.toString(), selectedIcon, mColor.color)
        val expenseCategories = databaseManager.getAllExpenseCategories()
        adapter.addCategory(expenseCategories.last())
        finish()
    }
    fun openColorPicker()
    {
        var colorPicker = AmbilWarnaDialog(this, mDefaultColor, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {
                // Действия при отмене выбора цвета
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                mDefaultColor = color
                mColorPreview.setBackgroundColor(mDefaultColor)
            }
        })
        colorPicker.show()
    }
}