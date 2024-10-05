package com.example.praktikum_papb

import com.google.gson.annotations.SerializedName

data class GithubUser(
    val login: String,
    val name : String,
    @SerializedName("avatar_url") val avatarUrl: String,
    @SerializedName("followers") val followersCount: Int,
    @SerializedName("following") val followingCount: Int
)
