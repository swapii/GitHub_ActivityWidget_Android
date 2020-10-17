package github.activity.feature.userActivity.domain

import java.util.Date

data class DailyUserActivity(
    val date: Date,
    val count: Int,
)
