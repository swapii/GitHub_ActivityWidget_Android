package com.github

import java.net.URL

class GetUserActivity internal constructor(
    private val getPageSource: GetPageSource,
    private val parseDayActivity: ParseDayActivity,
) {

    suspend operator fun invoke(username: String): List<OneDayActivityFromServer> {
        val pageSource = getPageSource(URL("https://github.com/$username"))
        val dayActivity = parseDayActivity(pageSource)
        if (dayActivity.isEmpty()) {
            throw PageSourceParseException("Can't find any activity on page")
        }
        return dayActivity
    }

}
