package com.falikiali.githubusersapp.data.remote

import com.falikiali.githubusersapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        req = req.newBuilder().header("Authorization", "token ${BuildConfig.TOKEN}").build()
        return chain.proceed(req)
    }
}