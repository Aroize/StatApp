package ru.ifmo.statapp.domain.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.ifmo.statapp.data.api.OAuthInfo

interface LoginIteractor {
    fun logIn(email: String, password: String): Single<OAuthInfo>

    fun logout(): Completable
}