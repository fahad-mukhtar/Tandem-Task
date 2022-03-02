package com.example.community.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.community.databinding.FragmentCommunityBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.community.presentation.adapters.CommunityDataAdapter
import com.example.community.presentation.adapters.CommunityLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CommunityFragment : Fragment() {
    val model: MainViewModel by viewModels()
    lateinit var binding:FragmentCommunityBinding
    lateinit var communityAdapter: CommunityDataAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCommunityBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = model

        setupList()
        setupView()

        return binding.root
    }

    private fun setupList() {
        communityAdapter = CommunityDataAdapter()
        binding.communityRv.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = communityAdapter.withLoadStateHeaderAndFooter(
                header = CommunityLoadStateAdapter { communityAdapter.retry() },
                footer = CommunityLoadStateAdapter { communityAdapter.retry() }
            )
            setHasFixedSize(true)
        }

    }

    private fun setupView() {

        lifecycleScope.launch {
            model.community.collectLatest { pagedData ->
                communityAdapter.submitData(pagedData)
            }
        }
    }

}