package github.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.github.DayActivityFromServer;

import org.androidannotations.annotations.EBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import github.activity.dao.DaoMaster;
import github.activity.dao.DaoSession;
import github.activity.dao.DayActivity;
import github.activity.dao.DayActivityDao;

/**
 * Created by asavinova on 28/03/15.
 */
@EBean(scope = EBean.Scope.Singleton)
public class Dao {

	private static final Logger L = LoggerFactory.getLogger(Dao.class);

	private DaoSession session;

	protected Dao(Context context) {
		DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "main", null);
		SQLiteDatabase db = helper.getWritableDatabase();
		DaoMaster master = new DaoMaster(db);
		session = master.newSession();
	}

	public void updateUserActivity(final String username, final List<DayActivityFromServer> userActivity) {
		//TODO Pass into method DayActivity for DB
		session.runInTx(new Runnable() {
			@Override
			public void run() {

				DayActivityDao dao = session.getDayActivityDao();
				L.trace("Activities count before update: " + dao.count());

				getBuilderForActivityBeUser(username)
						.buildDelete()
						.executeDeleteWithoutDetachingEntities();
				L.trace("Activities count after clean: " + dao.count());

				for (DayActivityFromServer serverActivity : userActivity) {
					DayActivity dbActivity = new DayActivity(username, serverActivity.getDate(), serverActivity.getActivityCount());
					dao.insert(dbActivity);
				}
				L.trace("Activities count after insert new: " + dao.count());

			}
		});
	}

	public List<DayActivity> getUserActivity(String username) {
		return getBuilderForActivityBeUser(username).build().list();
	}

	private QueryBuilder<DayActivity> getBuilderForActivityBeUser(String username) {
		return session.getDayActivityDao().queryBuilder()
				.where(DayActivityDao.Properties.User.eq(username));
	}

}
