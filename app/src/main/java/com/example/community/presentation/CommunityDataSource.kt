package com.example.community.presentation

import androidx.paging.PagingSource
import com.example.community.data.models.CommunityResponse
import com.example.community.data.models.Response
import com.example.community.data.networkLayer.CommunityApiService

class CommunityDataSource (private val api: CommunityApiService) : PagingSource<Int, Response>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Response> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response: CommunityResponse = api.getCommunity(nextPageNumber)

            LoadResult.Page(
                data = response.response,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (response.response.size <20 )  null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}