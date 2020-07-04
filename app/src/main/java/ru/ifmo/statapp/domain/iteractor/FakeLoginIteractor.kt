package ru.ifmo.statapp.domain.iteractor

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.ifmo.statapp.data.api.OAuthInfo
import ru.ifmo.statapp.domain.api.LoginIteractor

class FakeLoginIteractor : LoginIteractor {

    private val info = OAuthInfo("access_token", "token_type", "refresh_token", -1, "scope")

    override fun logIn(email: String, password: String): Single<OAuthInfo> {
        return Single.create { emitter -> emitter.onSuccess(info) }
    }

    override fun logout(): Completable = Completable.complete()
}