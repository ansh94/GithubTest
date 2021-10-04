package com.anshdeep.takehome.network.response


data class Repository(
    val description: String?,
    val forks_count: Int?,
    val name: String?,
    val stargazers_count: Int?,
    val updated_at: String?
)