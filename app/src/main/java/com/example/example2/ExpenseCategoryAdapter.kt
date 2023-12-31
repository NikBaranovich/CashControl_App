package com.example.example2

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.example2.databinding.CategoryDisplayItemBinding


class ExpenseCategoryAdapter: RecyclerView.Adapter<ExpenseCategoryAdapter.CategoryHolder>() {
    val categoryList = ArrayList<ExpenseCategoryData>()
    inner class CategoryHolder(item:View):RecyclerView.ViewHolder(item) {
        fun bindCategory(category:ExpenseCategoryData)
        {
            val binding = CategoryDisplayItemBinding.bind(itemView)
            binding.categoryImage.setImageResource(category.imageId)
            binding.categoryText.text = category.name
            binding.categoryCardView.setCardBackgroundColor(category.color)
            binding.categoryCardView.setOnClickListener{
                val context = this.itemView.context
                val intent = Intent(context, ExpensesByCategory::class.java)
                intent.putExtra("categoryId", category.id)
                context.startActivity(intent)
            }
        }
        fun bindButton(addButton: Button)
        {
            addButton?.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, ExpenseCategoryAdd::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = if(viewType == R.layout.category_display_item){
            LayoutInflater.from(parent.context).inflate(R.layout.category_display_item, parent, false)
        } else {
            LayoutInflater.from(parent.context).inflate(R.layout.category_add_button, parent, false)
        }
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size + 1
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        if (position == categoryList.size) {
            holder.bindButton(holder.itemView.findViewById<Button>(R.id.addButton))
        } else {
            holder.bindCategory(categoryList[position])
        }

    }

    fun updateCategories(newCategories: List<ExpenseCategoryData>) {
        categoryList.clear()
        categoryList.addAll(newCategories)
        notifyDataSetChanged()
    }
    fun addCategory(newCategory: ExpenseCategoryData) {
        categoryList.add(newCategory)
        notifyItemInserted(categoryList.size-1)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == categoryList.size) R.layout.category_add_button else R.layout.category_display_item
    }

}