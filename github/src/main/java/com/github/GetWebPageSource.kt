package com.github

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class GetWebPageSource(
    private val client: OkHttpClient
) {

    suspend operator fun invoke(url: URL): String =
        withContext(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            response.body!!.string()
        }

}
