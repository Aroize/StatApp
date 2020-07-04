package ru.ifmo.statapp.data.api

data class OAuthInfo(
    val accessToken: String,
    val tokenType: String,
    val refreshToken: String,
    val expiresIn: Int,
    val scope: String
)