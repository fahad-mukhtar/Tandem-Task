package com.example.community.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.community.Utilities.visible
import com.example.community.databinding.ItemLoaderBinding

class CommunityLoadStateAdapter (private val retry: () -> Unit) : LoadStateAdapter<CommunityLoadStateAdapter.CommunityLoadStateViewHolder>() {

    inner class CommunityLoadStateViewHolder(
        private val binding: ItemLoaderBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            binding.progressbar.visible(loadState is LoadState.Loading)
            binding.buttonRetry.visible(loadState is LoadState.Error)
            binding.buttonRetry.setOnClickListener {
                retry()
            }
            binding.progressbar.visibility = View.VISIBLE
        }
    }

    override fun onBindViewHolder(holder: CommunityLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = CommunityLoadStateViewHolder(
        ItemLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}