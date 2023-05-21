package ru.kpfu.itis.gnt.translationapitest.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.kpfu.itis.gnt.translationapitest.BuildConfig
import javax.inject.Inject


class ApiKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl = original.url
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key",  BuildConfig.API_KEY)
            .build()
        val requestBuilder = original.newBuilder()
            .url(url)
        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
