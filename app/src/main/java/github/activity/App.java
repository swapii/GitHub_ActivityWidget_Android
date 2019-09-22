package github.activity;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Intent;

import org.androidannotations.annotations.EApplication;
import org.androidannotations.annotations.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dagger.android.DaggerApplication;

/**
 * Created by pavel on 22/03/15.
 */
@EApplication
public class App extends Application {

	private static final Logger L = LoggerFactory.getLogger(App.class);

	public static final int DATA_UPDATE_INTERVAL = 1000 * 60 * 60;

	@SystemService AlarmManager alarmManager;

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

		Intent serviceIntent = new Intent(this, UpdateService_.class);
		PendingIntent alarmIntent = PendingIntent.getService(this, 0, serviceIntent, 0);

		alarmManager.setInexactRepeating(
				AlarmManager.ELAPSED_REALTIME,
				DATA_UPDATE_INTERVAL,
				DATA_UPDATE_INTERVAL,
				alarmIntent
		);
	}

}
