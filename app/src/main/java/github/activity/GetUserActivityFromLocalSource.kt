package github.activity

import github.activity.dao.DaoSession
import github.activity.dao.DayActivity
import github.activity.feature.userActivity.domain.DailyUserActivity
import github.activity.feature.widget.interactor.GetUserActivity

class GetUserActivityFromLocalSource(
    private val daoSession: DaoSession,
) : GetUserActivity {

    override operator fun invoke(username: String): List<DailyUserActivity> =
        daoSession.dayActivityDao
            .getBuilderForActivityByUser(username)
            .build()
            .list()
            .map { it.toDomain() }

    private fun DayActivity.toDomain(): DailyUserActivity =
        DailyUserActivity(
            date = date,
            count = count,
        )

}
