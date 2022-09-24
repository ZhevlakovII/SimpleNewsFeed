package com.izhxx.simplenewsfeed.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.izhxx.simplenewsfeed.adapters.NewsAdapter
import com.izhxx.simplenewsfeed.databinding.FragmentNewsFeedBinding
import com.izhxx.simplenewsfeed.utils.DEFAULT_CATEGORY
import com.izhxx.simplenewsfeed.utils.DEFAULT_COUNTRY
import com.izhxx.simplenewsfeed.utils.DEFAULT_PAGE_SIZE
import com.izhxx.simplenewsfeed.utils.LayoutSwapper
import com.izhxx.simplenewsfeed.viewmodels.NewsFeedViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class NewsFeedFragment : Fragment() {
    private val newsFeedViewModel: NewsFeedViewModel by viewModels()
    private lateinit var binding: FragmentNewsFeedBinding
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsFeedViewModel.getNews(DEFAULT_COUNTRY, DEFAULT_CATEGORY, DEFAULT_PAGE_SIZE, page)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        val newsItemAdapter = NewsAdapter()

        binding.newsFeed.adapter = newsItemAdapter

        observeLoadingStatus()
        observeNews(newsItemAdapter)
        observeErrors()


        return binding.root
    }

    private fun observeLoadingStatus() {
        newsFeedViewModel.loadingStatus.observe(viewLifecycleOwner) { status ->
            when (status) {
                true -> LayoutSwapper.swapper(binding.loadingProgress, binding.newsFeed)
                false -> LayoutSwapper.swapper(binding.newsFeed, binding.loadingProgress)
            }
        }
    }

    private fun observeNews(adapter: NewsAdapter) {
        newsFeedViewModel.news.observe(viewLifecycleOwner) { newsList ->
            adapter.submitList(newsList)
        }
    }

    private fun observeErrors() {
        newsFeedViewModel.error.observe(viewLifecycleOwner) {
            //While testing
            Toast.makeText(
                requireContext(),
                it,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}