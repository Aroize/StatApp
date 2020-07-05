package ru.ifmo.statapp.domain.api

import io.reactivex.Completable
import io.reactivex.Single
import ru.ifmo.statapp.data.api.OAuthInfo

interface LoginIteractor {
    fun logIn(email: String, password: String): Single<OAuthInfo>

    fun logout(): Completable
}