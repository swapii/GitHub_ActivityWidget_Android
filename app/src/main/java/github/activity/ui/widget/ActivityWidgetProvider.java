package github.activity.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import github.activity.R;
import github.activity.Storage_;
import github.activity.dao.DayActivity;
import github.activity.ui.ActivityColor;

/**
 * Created by asavinova on 26/12/14.
 */
public class ActivityWidgetProvider extends AppWidgetProvider {

	private static final Logger L = LoggerFactory.getLogger(ActivityWidgetProvider.class);

	public static void updateWidget(Context context, int widgetId) {

		AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

		String username = "swapii";
		List<DayActivity> userActivity = Storage_.getInstance_(context).getUserActivity(username);

		if (userActivity.size() < 365) {
			return;
		}

		Calendar finish = Calendar.getInstance();
		finish.add(Calendar.DAY_OF_MONTH, 7 - finish.get(Calendar.DAY_OF_WEEK) - 1);

		Calendar current = Calendar.getInstance();
		current.setTime(userActivity.get(userActivity.size() - 1).getDate());
		while (current.before(finish)) {
			userActivity.add(new DayActivity(username, current.getTime(), 0));
			current.add(Calendar.DAY_OF_MONTH, 1);
		}

		ActivityColor minColor = new ActivityColor(Color.parseColor("#30FFFFFF"));
		ActivityColor maxColor = new ActivityColor(Color.parseColor("#FFFFFFFF"));

		int maxActivity = findMaxActivity(userActivity);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

		List<Integer> ids = Arrays.asList(

				R.id.cell_00_0,
				R.id.cell_00_1,
				R.id.cell_00_2,
				R.id.cell_00_3,
				R.id.cell_00_4,
				R.id.cell_00_5,
				R.id.cell_00_6,

				R.id.cell_01_0,
				R.id.cell_01_1,
				R.id.cell_01_2,
				R.id.cell_01_3,
				R.id.cell_01_4,
				R.id.cell_01_5,
				R.id.cell_01_6,

				R.id.cell_02_0,
				R.id.cell_02_1,
				R.id.cell_02_2,
				R.id.cell_02_3,
				R.id.cell_02_4,
				R.id.cell_02_5,
				R.id.cell_02_6,

				R.id.cell_03_0,
				R.id.cell_03_1,
				R.id.cell_03_2,
				R.id.cell_03_3,
				R.id.cell_03_4,
				R.id.cell_03_5,
				R.id.cell_03_6,

				R.id.cell_04_0,
				R.id.cell_04_1,
				R.id.cell_04_2,
				R.id.cell_04_3,
				R.id.cell_04_4,
				R.id.cell_04_5,
				R.id.cell_04_6

		);

		Collections.reverse(ids);

		int idIndex = ids.size() - 1;
		int activityIndex = userActivity.size() - 1;

		while (idIndex >= 0 && activityIndex >= 0) {

			Integer id = ids.get(idIndex);
			DayActivity activity = userActivity.get(activityIndex);

			int color = ActivityColor.calculateColor(minColor, maxColor, maxActivity, activity.getCount());
			remoteViews.setInt(id, "setBackgroundColor", color);

			idIndex--;
			activityIndex--;
		}

		widgetManager.updateAppWidget(widgetId, remoteViews);
	}

	public static int findMaxActivity(List<DayActivity> userActivity) {
		int maxActivity = 0;
		for (DayActivity activity : userActivity) {
			maxActivity = Math.max(maxActivity, activity.getCount());
		}
		return maxActivity;
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager widgetManager, int[] widgetIds) {
		L.trace("On update");
		for (int widgetId : widgetIds) {
			updateWidget(context, widgetId);
		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		L.trace("Widget {} options changed", appWidgetId);
		updateWidget(context, appWidgetId);
	}

}
