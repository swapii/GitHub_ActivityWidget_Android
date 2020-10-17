package github.activity.ui.widget

import github.activity.feature.userActivity.domain.DailyUserActivity

interface GetUserActivity {

    operator fun invoke(username: String): List<DailyUserActivity>

}
