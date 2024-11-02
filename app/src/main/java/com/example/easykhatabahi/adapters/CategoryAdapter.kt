package com.example.easykhatabahi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easykhatabahi.R
import com.example.easykhatabahi.databinding.SampleCategoryItemBinding
import com.example.easykhatabahi.models.Category

class CategoryAdapter(
    var context: Context,
    categories: ArrayList<Category>,
    categoryClickListener: CategoryClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    var categories: ArrayList<Category>

    interface CategoryClickListener {
        fun onCategoryClicked(category: Category?)
    }

    var categoryClickListener: CategoryClickListener

    init {
        this.categories = categories
        this.categoryClickListener = categoryClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(
                context
            ).inflate(R.layout.sample_category_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category: Category = categories[position]
        holder.binding.categoryText.setText(category.categoryName)
        holder.binding.categoryIcon.setImageResource(category.categoryImage)
        holder.binding.categoryIcon.setBackgroundTintList(context.getColorStateList(category.categoryColor))
        holder.itemView.setOnClickListener { c: View? ->
            categoryClickListener.onCategoryClicked(
                category
            )
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: SampleCategoryItemBinding

        init {
            binding = SampleCategoryItemBinding.bind(itemView)
        }
    }
}
