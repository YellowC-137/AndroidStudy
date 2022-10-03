package com.example.roomwithviewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomwithviewmodel.databinding.RecyclerviewItemBinding

class WordAdapter : ListAdapter<Word, WordAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: RecyclerviewItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(items: Word){
            binding.textView.text = items.word
            binding.uid.text = items.uid.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Word>() {
            override fun areItemsTheSame(
                oldItem: Word,
                newItem: Word
            ): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(
                oldItem: Word,
                newItem: Word
            ): Boolean {
                return oldItem.uid == newItem.uid
            }

        }
    }

}