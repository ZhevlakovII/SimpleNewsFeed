package com.izhxx.simplenewsfeed.presenter.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.izhxx.simplenewsfeed.databinding.FragmentNewsFeedBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {
    private val newsFeedViewModel: NewsFeedViewModel by viewModels()

    private val newsAdapter by lazy(LazyThreadSafetyMode.NONE) {
        NewsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewsFeedBinding.inflate(inflater, container, false)

        binding.newsFeed.adapter = newsAdapter.withLoadStateHeaderAndFooter(
            header = NewsLoadStateAdapter(),
            footer = NewsLoadStateAdapter()
        )

        newsAdapter.addLoadStateListener { state ->
            with(binding) {
                newsFeed.isVisible = state.refresh != LoadState.Loading
                loadingProgress.isVisible = state.refresh == LoadState.Loading
            }
        }

        observeNews()

        return binding.root
    }

    private fun observeNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            newsFeedViewModel.news.collectLatest(newsAdapter::submitData)
        }
    }
}