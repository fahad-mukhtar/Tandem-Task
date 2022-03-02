package com.example.community.data.models

data class Response(
    val firstName: String,
    val id: Int,
    val learns: List<String>,
    val natives: List<String>,
    val pictureUrl: String,
    val referenceCnt: Int,
    val topic: String
)