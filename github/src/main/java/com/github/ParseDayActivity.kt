package com.github

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

class ParseDayActivity {

    suspend operator fun invoke(pageSource: String): List<OneDayActivityFromServer> =
        withContext(Dispatchers.Unconfined) {
            val result = mutableListOf<OneDayActivityFromServer>()

            val matcher = CELL_PATTERN.matcher(pageSource)
            while (matcher.find()) {
                val count = Integer.parseInt(matcher.group(1))
                val date = DATE_FORMAT.parse(matcher.group(2))
                result += OneDayActivityFromServer(date, count)
            }

            if (result.isEmpty()) {
                throw PageParseException("Can't find any activity on page")
            }

            result
        }

    companion object {
        private val CELL_PATTERN = Pattern.compile(
            "<rect class=\"day\" .+? data-count=\"(\\d+?)\" data-date=\"(\\d{4}-\\d{2}-\\d{2})\"/>",
            Pattern.DOTALL
        )
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
    }

}
