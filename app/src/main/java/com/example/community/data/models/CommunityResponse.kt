package com.example.community.data.models

data class CommunityResponse(
    val errorCode: Any?,
    val response: List<Response>,
    val type: String
)