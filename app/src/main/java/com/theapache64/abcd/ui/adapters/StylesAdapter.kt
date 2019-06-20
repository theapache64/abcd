package com.theapache64.abcd.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.abcd.databinding.ItemStyleBinding
import com.theapache64.abcd.models.Style

class StylesAdapter(
    context: Context,
    private val styles: List<Style>,
    private val callback: (position: Int) -> Unit
) : RecyclerView.Adapter<StylesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStyleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = styles.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val style = styles[position]
        holder.binding.style = style
    }

    inner class ViewHolder(val binding: ItemStyleBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(layoutPosition)
            }
        }
    }
}