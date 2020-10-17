package github.activity.ui.widget

import github.activity.dao.DayActivity

interface GetUserActivity {

    operator fun invoke(username: String): List<DayActivity>

}
