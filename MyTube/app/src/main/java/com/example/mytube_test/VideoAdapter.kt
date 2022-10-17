package com.example.mytube_test

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytube_test.databinding.ItemVideoBinding
import com.example.mytube_test.model.VideoX

class VideoAdapter(val callback:(String,String)->Unit) : ListAdapter<VideoX, VideoAdapter.VideoViewHolder>(diffUtil) {

    inner class VideoViewHolder(private val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoX) {
            binding.titleTextView.text = item.title
            binding.subTitleTextView.text = item.subtitle
            Glide.with(binding.thumbnailImageView.context)
                .load(item.thumb).into(binding.thumbnailImageView)
            binding.thumbnailImageView.setOnClickListener {
                callback(item.sources,item.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<VideoX>() {
            override fun areItemsTheSame(oldItem: VideoX, newItem: VideoX): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: VideoX, newItem: VideoX): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    }


}