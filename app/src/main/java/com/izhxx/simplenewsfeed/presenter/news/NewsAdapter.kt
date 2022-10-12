package com.izhxx.simplenewsfeed.presenter.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.izhxx.simplenewsfeed.R
import com.izhxx.simplenewsfeed.data.api.Articles
import com.izhxx.simplenewsfeed.databinding.NewsFeedItemBinding

class NewsAdapter : PagingDataAdapter<Articles, NewsViewHolder>(NewsDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsFeedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class NewsViewHolder(private val binding: NewsFeedItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Articles?) {
            with(binding) {
                article = item
                newsDate.text = getDate(item?.publishedDate)
                Glide
                    .with(root)
                    .load(item?.imageUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_image_load_failed)
                    .into(newsImage)
            }
        }

    private fun getDate(publishedDate: String?): CharSequence? {
        val dateArray = publishedDate?.split('T', 'Z') ?: mutableListOf()

        return if (dateArray.isEmpty()) {
            ""
        } else {
            "${dateArray[0]} ${dateArray[1]}"
        }
    }
}

private class NewsDiffCallback : DiffUtil.ItemCallback<Articles>() {

    override fun areItemsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Articles, newItem: Articles): Boolean {
        return oldItem.title == newItem.title && oldItem.newsUrl == newItem.newsUrl
    }
}
