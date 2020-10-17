package github.activity.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import github.activity.R;
import github.activity.dao.DayActivity;
import github.activity.ui.ActivityColor;
import github.activity.ui.PreferencesActivity;

public class ActivityWidgetProvider extends AppWidgetProvider {

	private static final Logger L = LoggerFactory.getLogger(ActivityWidgetProvider.class);

	@Inject
    GetUserActivity getUserActivity;

	@Override
	public void onUpdate(Context context, AppWidgetManager widgetManager, int[] widgetIds) {
		L.trace("On update");
		inject(context);
		for (int widgetId : widgetIds) {
			updateWidget(context, widgetId);
		}
	}

	private void inject(Context context) {
	    if (getUserActivity != null) {
	        // Already injected
	        return;
        }
		((WidgetComponent.Provider) context.getApplicationContext()).getWidgetComponent().inject(this);
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		L.trace("Widget {} options changed", appWidgetId);
		inject(context);
		updateWidget(context, appWidgetId);
	}

	public void updateWidget(Context context, int widgetId) {

		AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

		ActivityWidget widget = new ActivityWidget(context, widgetId);

		String username = widget.getUsername();
		List<DayActivity> userActivity = getUserActivity.invoke(username);

		if (userActivity.size() < 365) {
			return;
		}

		fillUserActivityToFullWeek(username, userActivity);

		Intent preferencesActivityIntent = PreferencesActivity.createIntent(context, widgetId);

		PendingIntent intent = PendingIntent.getActivity(
				context, widgetId,
				preferencesActivityIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		remoteViews.setOnClickPendingIntent(R.id.container, intent);
		setWeeksVisibility(remoteViews, widget);
		setCellsColor(remoteViews, widget, userActivity);
		widgetManager.updateAppWidget(widgetId, remoteViews);
	}

	private void fillUserActivityToFullWeek(String username, List<DayActivity> userActivity) {

		Calendar lastDay = Calendar.getInstance();
		lastDay = DateUtils.truncate(lastDay, Calendar.DAY_OF_MONTH);
		L.trace("Today: {}", lastDay.getTime().toString());

		//TODO Get preferred last day of week from user preferences
		int preferredLastDayOfWeek = Calendar.SUNDAY;
		while (lastDay.get(Calendar.DAY_OF_WEEK) != preferredLastDayOfWeek) {
			lastDay.add(Calendar.DAY_OF_WEEK, 1);
		}

		DayActivity lastSavedUserActivity = userActivity.get(userActivity.size() - 1);

		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(lastSavedUserActivity.getDate());
		currentDay = DateUtils.truncate(currentDay, Calendar.DAY_OF_MONTH);

		while (currentDay.before(lastDay)) {
			L.trace("Add fictive day: {}", currentDay.getTime().toString());
			userActivity.add(new DayActivity(username, currentDay.getTime(), 0));
			currentDay.add(Calendar.DAY_OF_MONTH, 1);
		}

	}

	private void setCellsColor(RemoteViews remoteViews, ActivityWidget widget, List<DayActivity> userActivity) {
		List<Integer> cellIds = widget.getCellViewIdsDesc();

		ActivityColor minColor = ActivityColor.Companion.fromIntValue(Color.parseColor("#30FFFFFF"));
		ActivityColor maxColor = ActivityColor.Companion.fromIntValue(Color.parseColor("#FFFFFFFF"));

		int maxActivity = findMaxActivity(userActivity);

		int idIndex = cellIds.size() - 1;
		int activityIndex = userActivity.size() - 1;

		// Walk from last week to earlier until views end or activity end
		while (idIndex >= 0 && activityIndex >= 0) {

			Integer id = cellIds.get(idIndex);
			DayActivity activity = userActivity.get(activityIndex);

			int color = ActivityColor.Companion.calculateColor(minColor, maxColor, maxActivity, activity.getCount());
			remoteViews.setInt(id, "setBackgroundColor", color);

			idIndex--;
			activityIndex--;
		}
	}

	private void setWeeksVisibility(RemoteViews remoteViews, ActivityWidget widget) {
		List<Integer> weekViewIds = widget.getWeekViewIds();

		int visibleWeeksCount = widget.getVisibleWeeksCount();

		for (int week = 0; week < weekViewIds.size(); week++) {
			Integer weekId = weekViewIds.get(week);
			int visibility = week < visibleWeeksCount ? View.VISIBLE : View.GONE;
			remoteViews.setViewVisibility(weekId, visibility);
		}
	}

	public int findMaxActivity(List<DayActivity> userActivity) {
		int maxActivity = 0;
		for (DayActivity activity : userActivity) {
			maxActivity = Math.max(maxActivity, activity.getCount());
		}
		return maxActivity;
	}

}
