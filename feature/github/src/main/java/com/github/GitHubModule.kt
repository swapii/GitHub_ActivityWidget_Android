package com.github

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class GitHubModule(
    private val okHttpClient: OkHttpClient,
) {

    @Provides
    internal fun provideGetUserActivity(
        getPageSource: GetPageSource,
        parseDayActivity: ParseDayActivity,
    ) =
        GetUserActivity(
            getPageSource,
            parseDayActivity,
        )

    @Provides
    internal fun provideGetPageSource() =
        GetPageSource(okHttpClient)

    @Provides
    internal fun provideParseDayActivity() =
        ParseDayActivity()

}
