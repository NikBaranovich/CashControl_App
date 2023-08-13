package com.example.example2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.example2.databinding.ExpenseByCategoryItemBinding
import java.text.SimpleDateFormat
import java.util.Locale

class ExpensesByCategoryAdapter: RecyclerView.Adapter<ExpensesByCategoryAdapter.ExpensesByCategoryHolder>() {
    val expensesList = ArrayList<ExpenseData>()
    class ExpensesByCategoryHolder(item: View):RecyclerView.ViewHolder(item) {

        fun bind(expense:ExpenseData)
        {
            val binding = ExpenseByCategoryItemBinding.bind(itemView)
            binding.expenseAmountText.text = expense.expenseValue.toString();
            val expenseDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru")) // Устанавливаем нужный формат и локаль
            val formattedDate = expenseDateFormat.format(expense.expenseDate)
            binding.dateText.text = formattedDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesByCategoryHolder {
        return ExpensesByCategoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.expense_by_category_item, parent, false))
    }

    override fun getItemCount(): Int {
        return expensesList.size
    }

    override fun onBindViewHolder(holder: ExpensesByCategoryHolder, position: Int) {
        holder.bind(expensesList[position])
    }
    fun updateExpenses(newExpenses: List<ExpenseData>) {
        expensesList.clear()
        expensesList.addAll(newExpenses)
        notifyDataSetChanged()
    }
}