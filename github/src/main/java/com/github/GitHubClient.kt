package com.github

import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.rx2.rxSingle
import okhttp3.OkHttpClient
import java.net.URL

class GitHubClient {

    fun getUserActivity(username: String): Single<List<OneDayActivityFromServer>> =
        getPageSource(URL("https://github.com/$username"))
            .flatMap { pageSource -> parseDayActivity(pageSource) }

    private fun getPageSource(url: URL): Single<String> =
        RxJavaBridge.toV3Single(
            rxSingle {
                GetWebPageSource(OkHttpClient())(url)
            }
        )

    private fun parseDayActivity(pageSource: String): Single<List<OneDayActivityFromServer>> =
        RxJavaBridge.toV3Single(
            rxSingle {
                ParseDayActivity()(pageSource)
            }
        )

}
