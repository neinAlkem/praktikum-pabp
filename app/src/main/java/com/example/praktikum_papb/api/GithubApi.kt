package com.example.praktikum_papb.api

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET("users/{username}")
    suspend fun getUser(@Path("username") username:String) : GithubUser
}