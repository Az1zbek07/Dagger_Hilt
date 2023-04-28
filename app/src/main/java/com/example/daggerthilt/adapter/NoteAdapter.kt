package com.example.daggerthilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.daggerthilt.databinding.NoteLayoutBinding
import com.example.daggerthilt.model.NoteResponseItem

class NoteAdapter: ListAdapter<NoteResponseItem, NoteAdapter.VHolder>(DiffCallback()) {
    lateinit var onClick: (noteResponseItem: NoteResponseItem) -> Unit
    private class DiffCallback: DiffUtil.ItemCallback<NoteResponseItem>(){
        override fun areItemsTheSame(
            oldItem: NoteResponseItem,
            newItem: NoteResponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: NoteResponseItem,
            newItem: NoteResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder(NoteLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VHolder(private val binding: NoteLayoutBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(noteResponseItem: NoteResponseItem){
            binding.textView.text = noteResponseItem.title
            itemView.setOnClickListener {
                onClick(noteResponseItem)
            }
        }
    }
}