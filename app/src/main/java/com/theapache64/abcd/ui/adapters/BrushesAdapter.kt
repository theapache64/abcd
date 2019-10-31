package com.theapache64.abcd.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theapache64.abcd.databinding.ItemBrushBinding
import com.theapache64.abcd.models.Brush

class BrushesAdapter(
    context: Context,
    private val brushes: List<Brush>,
    private val callback: (brush: Brush) -> Unit
) : RecyclerView.Adapter<BrushesAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBrushBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = brushes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val brush = brushes[position]
        holder.binding.brush = brush
    }

    inner class ViewHolder(val binding: ItemBrushBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                callback(brushes[layoutPosition])
            }
        }
    }
}