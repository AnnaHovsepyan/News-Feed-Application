package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.myapplication.adapter.NewsAdapter
import com.example.myapplication.adapter.OnCheckBoxClickListener
import com.example.myapplication.databinding.FragmentNewsFeedBinding
import com.example.myapplication.model.NewsFeedPost
import com.example.myapplication.viewModel.FavoritesViewModel
import com.example.myapplication.viewModel.NewsFeedViewModel
import com.example.myapplication.viewModelFactory.FavoritesViewModelFactory
import com.example.myapplication.viewModelFactory.NewsFeedViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter

class NewsFeedFragment : Fragment(), OnCheckBoxClickListener {
    private var favoriteNewsIds = mutableListOf<String>()
    private lateinit var binding: FragmentNewsFeedBinding
    private lateinit var adapter: NewsAdapter
    private val favoritesViewModel: FavoritesViewModel by viewModels {
        FavoritesViewModelFactory.create(requireContext())
    }
    private val newsFeedViewModel: NewsFeedViewModel by viewModels {
        NewsFeedViewModelFactory.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsFeedBinding.inflate(layoutInflater)
        val root = binding.root
        favoritesViewModel.isFavorite()
        favoritesViewModel.result.observe(viewLifecycleOwner, {
            favoriteNewsIds = it.toMutableList()
        })
        initAdapter()
        initSwipeToRefresh()
        return root
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
            newsFeedViewModel.newsFeedPagingData.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvNewsFeed.scrollToPosition(0) }
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { adapter.refresh() }
    }

    override fun onCheckBoxClick(checked: Boolean, newsFeedPost: NewsFeedPost) {
        if (checked) {
            favoritesViewModel.insert(newsFeedPost)
        } else favoritesViewModel.remove(newsFeedPost.id)
    }

    override fun isFavorite(id: String) = favoriteNewsIds.contains(id)
}