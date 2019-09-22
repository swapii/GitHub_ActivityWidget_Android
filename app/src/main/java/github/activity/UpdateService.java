package github.activity;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.github.OneDayActivityFromServer;
import com.github.GitHubClient;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;
import org.androidannotations.annotations.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import github.activity.ui.widget.ActivityWidgetProvider;
import github.activity.ui.widget.WidgetOptions;

/**
 * Created by asavinova on 28/03/15.
 */
@EService
public class UpdateService extends Service {

	private static final Logger L = LoggerFactory.getLogger(UpdateService.class);

	@Bean Storage storage;

	@SystemService ConnectivityManager connectivityManager;

	private boolean isStarted;

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

			AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

			Set<String> usernameSet = new HashSet<>();

			ComponentName componentName = new ComponentName(this, ActivityWidgetProvider.class);
			for (int widgetId : widgetManager.getAppWidgetIds(componentName)) {
                WidgetOptions options = new WidgetOptions(widgetManager.getAppWidgetOptions(widgetId));
                usernameSet.add(options.getUsername());
            }

			for (String username : usernameSet) {
                try {
                    GitHubClient client = new GitHubClient();
                    List<OneDayActivityFromServer> userActivity = client.getUserActivity(username)
						.toList()
						.blockingGet();
                    storage.updateUserActivity(username, userActivity);
				} catch (GitHubClient.PageParseException e) {
                    e.printStackTrace();
                }
            }

			for (int widgetId : widgetManager.getAppWidgetIds(componentName)) {
				ActivityWidgetProvider.updateWidget(this, widgetId);
			}

		} finally {
			stopSelf();
		}
	}

}
