package github.activity;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.github.DayActivityFromServer;
import com.github.GitHubClient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.ui.widget.ActivityWidgetProvider;

/**
 * Created by asavinova on 28/03/15.
 */
@EService
public class UpdateService extends Service {

	private static final Logger L = LoggerFactory.getLogger(UpdateService.class);

	private boolean isStarted;

	@Bean Storage storage;
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
		L.trace("Update data");
		try {
			GitHubClient client = new GitHubClient();
			List<DayActivityFromServer> userActivity = client.getUserActivity("swapii");
			storage.updateUserActivity("swapii", userActivity);

			AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

			ComponentName componentName = new ComponentName(this, ActivityWidgetProvider.class);

			for (int widgetId : widgetManager.getAppWidgetIds(componentName)) {
				L.info("Widget ID: {}", widgetId);
				ActivityWidgetProvider.updateWidget(this, widgetId);
			}

		} catch (GitHubClient.PageParseException e) {
			e.printStackTrace();
		} finally {
			stopSelf();
		}
	}

}
