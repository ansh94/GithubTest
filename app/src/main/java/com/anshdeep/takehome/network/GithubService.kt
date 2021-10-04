package com.anshdeep.takehome.network

import com.anshdeep.takehome.network.response.Repository
import com.anshdeep.takehome.network.response.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface GithubService {

    @GET("users/{userId}")
    suspend fun getUser(
        @Path("userId") userId: String
    ): Response<User>

    @GET("users/{userId}/repos")
    suspend fun getUserRepositories(
        @Path("userId") userId: String
    ): Response<List<Repository>>

}