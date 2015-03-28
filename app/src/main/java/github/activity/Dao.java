package github.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.androidannotations.annotations.EBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.client.DayActivity;
import github.activity.dao.DaoMaster;
import github.activity.dao.DaoSession;
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

	public void updateUserActivity(final String username, final List<DayActivity> userActivity) {
		session.runInTx(new Runnable() {
			@Override
			public void run() {

				DayActivityDao dao = session.getDayActivityDao();
				L.trace("Activities count before update: " + dao.count());

				dao.queryBuilder()
						.where(DayActivityDao.Properties.User.eq(username))
						.buildDelete()
						.executeDeleteWithoutDetachingEntities();
				L.trace("Activities count after clean: " + dao.count());

				for (DayActivity serverActivity : userActivity) {
					github.activity.dao.DayActivity dbActivity = new github.activity.dao.DayActivity(username, serverActivity.getDate(), serverActivity.getActivityCount());
					dao.insert(dbActivity);
				}
				L.trace("Activities count after insert new: " + dao.count());

			}
		});
	}

}
