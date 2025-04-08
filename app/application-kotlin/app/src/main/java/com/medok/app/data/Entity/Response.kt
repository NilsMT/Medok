package com.medok.app.data.Entity

import kotlinx.serialization.Serializable

@Serializable
data class Response<T>(
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int,
    val data: List<T>
)