package github.activity

import github.activity.dao.DaoSession
import github.activity.dao.DayActivity

class GetUserActivityFromLocalSource(
    private val daoSession: DaoSession,
) {

    operator fun invoke(username: String): List<DayActivity> =
        daoSession.dayActivityDao
            .getBuilderForActivityByUser(username)
            .build()
            .list()

}
