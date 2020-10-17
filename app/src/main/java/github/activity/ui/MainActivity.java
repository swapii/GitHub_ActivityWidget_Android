package github.activity.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.activity.R;
import github.activity.UpdateAllUsersDayActivitiesWorker;
import github.activity.feature.widget.provider.ActivityWidgetProvider;

public class MainActivity extends AppCompatActivity {

	private static final Logger L = LoggerFactory.getLogger(MainActivity.class);

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		L.trace("On resume");

		ComponentName componentName = new ComponentName(this, ActivityWidgetProvider.class);
		AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

		for (int widgetId : widgetManager.getAppWidgetIds(componentName)) {
			L.info("Widget ID: {}", widgetId);
		}

        WorkManager.getInstance(this)
            .enqueue(OneTimeWorkRequest.from(UpdateAllUsersDayActivitiesWorker.class));

	}

}
