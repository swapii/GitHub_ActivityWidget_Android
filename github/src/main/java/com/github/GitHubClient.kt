package com.github

import hu.akarnokd.rxjava3.bridge.RxJavaBridge
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.rx2.rxSingle
import okhttp3.OkHttpClient
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

class GitHubClient {

    fun getUserActivity(username: String): Observable<OneDayActivityFromServer> =
        getPageSource(URL("https://github.com/$username"))
            .flatMapObservable { pageSource -> parseDayActivity(pageSource) }
            .switchIfEmpty(Observable.error(PageParseException("Can't find any activity on page")))

    private fun getPageSource(url: URL): Single<String> =
        RxJavaBridge.toV3Single(
            rxSingle {
                GetWebPageSource(OkHttpClient()).invoke(url)
            }
        )

    private fun parseDayActivity(pageSource: String): Observable<OneDayActivityFromServer> =
        Observable.create { emitter ->
            val matcher = CELL_PATTERN.matcher(pageSource)
            while (matcher.find()) {
                val count = Integer.parseInt(matcher.group(1))
                val date = DATE_FORMAT.parse(matcher.group(2))
                emitter.onNext(OneDayActivityFromServer(date, count))
            }
            emitter.onComplete()
        }

    inner class PageParseException(s: String) : RuntimeException(s)

    companion object {
        private val CELL_PATTERN = Pattern.compile(
            "<rect class=\"day\" .+? data-count=\"(\\d+?)\" data-date=\"(\\d{4}-\\d{2}-\\d{2})\"/>",
            Pattern.DOTALL
        )
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    }

}
