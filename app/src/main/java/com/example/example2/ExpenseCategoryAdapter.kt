package com.example.example2

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.example2.databinding.CategoryDisplayItemBinding

class ExpenseCategoryAdapter: RecyclerView.Adapter<ExpenseCategoryAdapter.CategoryHolder>() {
    val categoryList = ArrayList<ExpenseCategoryData>()
    class CategoryHolder(item:View):RecyclerView.ViewHolder(item) {
        val binding = CategoryDisplayItemBinding.bind(item)
        fun bind(category:ExpenseCategoryData) = with(binding)
        {
            categoryImage.setImageResource(category.imageId)
            categoryText.text = category.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_display_item, parent, false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    fun updateCategories(newCategories: List<ExpenseCategoryData>) {
        categoryList.clear()
        categoryList.addAll(newCategories)
        notifyDataSetChanged()
    }
    fun addCategory(newCategory: ExpenseCategoryData) {
        categoryList.add(newCategory)
        notifyDataSetChanged()
    }
}