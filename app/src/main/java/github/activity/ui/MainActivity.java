package github.activity.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.activity.R;
import github.activity.UpdateService_;
import github.activity.ui.widget.ActivityWidgetProvider;

@EActivity(R.layout.main)
public class MainActivity extends AppCompatActivity {

	private static final Logger L = LoggerFactory.getLogger(MainActivity.class);

	@Override
	protected void onResume() {
		super.onResume();
		L.trace("On resume");

		ComponentName componentName = new ComponentName(this, ActivityWidgetProvider.class);
		AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

		for (int widgetId : widgetManager.getAppWidgetIds(componentName)) {
			L.info("Widget ID: {}", widgetId);
		}

		Intent intent = new Intent(this, UpdateService_.class);
		startService(intent);

	}

}
