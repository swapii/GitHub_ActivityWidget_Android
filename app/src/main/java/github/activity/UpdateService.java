package github.activity;

import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.client.DayActivity;
import github.activity.client.GitHubClient;

/**
 * Created by asavinova on 28/03/15.
 */
@EService
public class UpdateService extends Service {

	private static final Logger L = LoggerFactory.getLogger(UpdateService.class);

	private boolean isStarted;

	@Bean Dao dao;
	@SystemService ConnectivityManager connectivityManager;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if (isStarted) {
			return START_NOT_STICKY;
		}

		isStarted = true;

		updateData();

		return START_NOT_STICKY;
	}

	@Background
	void updateData() {

		try {

			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			if (networkInfo == null || networkInfo.getType() != ConnectivityManager.TYPE_WIFI) {
				return;
			}

			GitHubClient client = new GitHubClient();
			String username = "swapii";
			List<DayActivity> userActivity = client.getUserActivity(username);
			dao.updateUserActivity(username, userActivity);

		} finally {
			stopSelf();
		}

	}

}
