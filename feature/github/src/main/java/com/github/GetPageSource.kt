package com.github

import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

internal class GetPageSource(
    private val okHttpClient: OkHttpClient,
) {

    suspend operator fun invoke(url: URL): String {
        val request = Request.Builder()
            .url(url)
            .build()
        val call = okHttpClient.newCall(request)
        return call.execute().use { response ->
            response.body!!.string()
        }
    }

}
