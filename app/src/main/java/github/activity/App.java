package github.activity;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.content.ContextCompat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App extends Application {

	private static final Logger L = LoggerFactory.getLogger(App.class);

	public static final int DATA_UPDATE_INTERVAL = 1000 * 60 * 60;

	private ApplicationComponent component;

	@Override
	public void onCreate() {
		super.onCreate();
		L.trace("App create");
		tuneAlarm();

		component = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationComponent.ApplicationModule(this))
				.build();

	}

	public ApplicationComponent getComponent() {
		return component;
	}

	private void tuneAlarm() {

		Intent serviceIntent = new Intent(this, UpdateService.class);
		PendingIntent alarmIntent = PendingIntent.getService(this, 0, serviceIntent, 0);

		ContextCompat.getSystemService(this, AlarmManager.class)
				.setInexactRepeating(
						AlarmManager.ELAPSED_REALTIME,
						DATA_UPDATE_INTERVAL,
						DATA_UPDATE_INTERVAL,
						alarmIntent
				);

	}

}
