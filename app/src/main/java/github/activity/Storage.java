package github.activity;

import com.github.OneDayActivityFromServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Provider;

import github.activity.dao.DaoSession;
import github.activity.dao.DayActivity;
import github.activity.dao.DayActivityDao;

public class Storage {

	private static final Logger L = LoggerFactory.getLogger(Storage.class);

	private final Provider<DaoSession> sessionProvider;

	protected Storage(Provider<DaoSession> sessionProvider) {
        this.sessionProvider = sessionProvider;
    }

	public void updateUserActivity(final String username, final List<OneDayActivityFromServer> userActivity) {

		if (L.isTraceEnabled()) {
			L.trace("User: {}", username);
			for (OneDayActivityFromServer activity : userActivity) {
				L.trace("  Activity: {} | {}", activity.getDate().toString(), activity.getActivityCount());
			}
		}

        DaoSession session = sessionProvider.get();

		//TODO Pass into method DayActivity for DB
		session.runInTx(() -> {

            DayActivityDao dao = session.getDayActivityDao();
            L.trace("Activities count before update: " + dao.count());

            DayActivityDao_getBuilderForActivityByUserKt.getBuilderForActivityByUser(
                session.getDayActivityDao(),
                username
            )
                    .buildDelete()
                    .executeDeleteWithoutDetachingEntities();
            L.trace("Activities count after clean: " + dao.count());

            for (OneDayActivityFromServer serverActivity : userActivity) {
                DayActivity dbActivity = new DayActivity(username, serverActivity.getDate(), serverActivity.getActivityCount());
                dao.insert(dbActivity);
            }
            L.trace("Activities count after insert new: " + dao.count());

        });
	}

}
