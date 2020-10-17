package github.activity

import de.greenrobot.dao.query.QueryBuilder
import github.activity.dao.DayActivity
import github.activity.dao.DayActivityDao

fun DayActivityDao.getBuilderForActivityByUser(username: String): QueryBuilder<DayActivity> =
    this
        .queryBuilder()
        .where(DayActivityDao.Properties.User.eq(username))
