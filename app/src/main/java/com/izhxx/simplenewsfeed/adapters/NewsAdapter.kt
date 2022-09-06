package com.izhxx.simplenewsfeed.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izhxx.simplenewsfeed.R
import com.izhxx.simplenewsfeed.data.entities.NewsEntity
import com.izhxx.simplenewsfeed.databinding.NewsFeedItemBinding
import kotlin.random.Random

class NewsAdapter : ListAdapter<NewsEntity, RecyclerView.ViewHolder>(NewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(
            NewsFeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as NewsViewHolder).bind(item)
    }

    class NewsViewHolder(
        private val binding: NewsFeedItemBinding
        ): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { }
        }

        fun bind(item: NewsEntity) {
            binding.apply {
                news = item

                binding.newsDate.text = item.publishedDate.split('T')[0]
                binding.iconRandom.setImageResource(getRandomIcon())

                Glide
                    .with(binding.root)
                    .load(item.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_load_failed)
                    .into(binding.newsImage)
            }
        }

        fun getRandomIcon(): Int {

            return when (Random.nextInt(0, 4)) {
                1 -> R.drawable.ic_apple
                2 -> R.drawable.ic_cherries
                3 -> R.drawable.ic_cantaloupe
                4 -> R.drawable.ic_image_load_failed
                0 -> R.drawable.ic_launcher_foreground
                else -> R.drawable.ic_launcher_background
            }
        }
    }
}

private class NewsDiffCallback: DiffUtil.ItemCallback<NewsEntity>() {
    override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
        return oldItem == newItem
    }

}
