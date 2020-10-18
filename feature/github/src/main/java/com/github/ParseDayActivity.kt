package com.github

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.regex.Pattern

internal class ParseDayActivity {

    operator fun invoke(pageSource: String): List<OneDayActivityFromServer> {
        val activities = mutableListOf<OneDayActivityFromServer>()
        val matcher = CELL_PATTERN.matcher(pageSource)
        while (matcher.find()) {
            val count = Integer.parseInt(matcher.group(1))
            val date = DATE_FORMAT.parse(matcher.group(2))
            activities += OneDayActivityFromServer(date, count)
        }
        return activities.toList()
    }

    companion object {

        private val CELL_PATTERN = Pattern.compile(
            "<rect class=\"day\" .+? data-count=\"(\\d+?)\" data-date=\"(\\d{4}-\\d{2}-\\d{2})\"/>",
            Pattern.DOTALL,
        )

        private val DATE_FORMAT = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.ROOT,
        )

    }

}
