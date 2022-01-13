package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.adapter.OnCheckBoxClickListener
import com.example.myapplication.databinding.FragmentNewsFeedBinding
import com.example.myapplication.model.NewsFeedPost
import com.example.myapplication.viewModel.FavoritesViewModel
import com.example.myapplication.viewModelFactory.FavoritesViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(), OnCheckBoxClickListener {
    private lateinit var adapter: NewsAdapter
    private lateinit var binding: FragmentNewsFeedBinding

    private val favoritesViewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory.create(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(layoutInflater)
        initSwipeToRefresh()
        initAdapter()
        getFavorites()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.refresh()
    }

    private fun initAdapter() {
        adapter = NewsAdapter(this)
        binding.rvNewsFeed.adapter = adapter
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }
        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvNewsFeed.scrollToPosition(0) }
        }
    }

    private fun getFavorites() {
        lifecycleScope.launch {
            favoritesViewModel.newsPagingData.collectLatest {
                adapter.submitData(lifecycle, PagingData.empty())
                adapter.submitData(it)
            }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    override fun onCheckBoxClick(checked: Boolean, newsFeedPost: NewsFeedPost) {
        favoritesViewModel.remove(newsFeedPost.id)
        adapter.refresh()
    }

    override fun isFavorite(id: String) = true
}