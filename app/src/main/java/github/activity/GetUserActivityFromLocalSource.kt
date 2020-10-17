package github.activity

import github.activity.dao.DaoSession
import github.activity.dao.DayActivity
import github.activity.ui.widget.GetUserActivity

class GetUserActivityFromLocalSource(
    private val daoSession: DaoSession,
): GetUserActivity {

    override operator fun invoke(username: String): List<DayActivity> =
        daoSession.dayActivityDao
            .getBuilderForActivityByUser(username)
            .build()
            .list()

}
