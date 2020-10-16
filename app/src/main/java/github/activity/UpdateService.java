package github.activity;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import com.github.GitHubClient;
import com.github.OneDayActivityFromServer;
import com.github.PageParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import github.activity.ui.widget.ActivityWidgetProvider;
import github.activity.ui.widget.WidgetOptions;

public class UpdateService extends Service {

	private static final Logger L = LoggerFactory.getLogger(UpdateService.class);

	@Inject Storage storage;

	private boolean isStarted;

	@Override
	public void onCreate() {
		super.onCreate();
		((App) getApplication()).getComponent().inject(this);
	}

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

		new Thread(this::updateData).start();

		return START_NOT_STICKY;
	}

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
						.blockingGet();
                    storage.updateUserActivity(username, userActivity);
				} catch (PageParseException e) {
                    e.printStackTrace();
                }
            }

		} finally {
			stopSelf();
		}
	}

}
