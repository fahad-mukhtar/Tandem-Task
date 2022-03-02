package com.example.community.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.community.data.networkLayer.CommunityApi

class MainViewModel : ViewModel(){

    val community =
        Pager(config = PagingConfig(pageSize = 10, prefetchDistance = 2), pagingSourceFactory = {
            CommunityDataSource(CommunityApi.retrofitService)
        }).flow.cachedIn(viewModelScope)


}