package github.activity.ui;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.EActivity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import github.activity.R;
import github.activity.ui.widget.ActivityWidgetProvider;


@EActivity(R.layout.main)
public class MainActivity extends ActionBarActivity {

    private static final Logger L = LoggerFactory.getLogger(MainActivity.class);

	@Override
	protected void onResume() {
		super.onResume();

		ComponentName componentName = new ComponentName(this, ActivityWidgetProvider.class);
		AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);

		for (int widgetId : widgetManager.getAppWidgetIds(componentName)) {
			L.info("Widget ID: {}", widgetId);
		}

	}

}
