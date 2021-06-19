package com.skydoves.marvelheroes.model


data class BaseResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Articles>
)