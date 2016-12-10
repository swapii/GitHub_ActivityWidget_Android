package github.activity.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
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

		ActivityWidget widget = new ActivityWidget(context, widgetId);

		String username = widget.getUsername();
		List<DayActivity> userActivity = Storage_.getInstance_(context).getUserActivity(username);

		if (userActivity.size() < 365) {
			return;
		}

		fillUserActivityToFullWeek(username, userActivity);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		setWeeksVisibility(remoteViews, widget);
		setCellsColor(remoteViews, widget, userActivity);
		widgetManager.updateAppWidget(widgetId, remoteViews);
	}

	private static void fillUserActivityToFullWeek(String username, List<DayActivity> userActivity) {
		Calendar lastDay = Calendar.getInstance();
		lastDay.add(Calendar.DAY_OF_MONTH, 7 - lastDay.get(Calendar.DAY_OF_WEEK) - 1);

		Calendar currentDay = Calendar.getInstance();
		currentDay.setTime(userActivity.get(userActivity.size() - 1).getDate());

		while (currentDay.before(lastDay)) {
			userActivity.add(new DayActivity(username, currentDay.getTime(), 0));
			currentDay.add(Calendar.DAY_OF_MONTH, 1);
		}
	}

	private static void setCellsColor(RemoteViews remoteViews, ActivityWidget widget, List<DayActivity> userActivity) {
		List<Integer> cellIds = widget.getCellViewIdsDesc();

		ActivityColor minColor = new ActivityColor(Color.parseColor("#30FFFFFF"));
		ActivityColor maxColor = new ActivityColor(Color.parseColor("#FFFFFFFF"));

		int maxActivity = findMaxActivity(userActivity);

		int idIndex = cellIds.size() - 1;
		int activityIndex = userActivity.size() - 1;

		// Walk from last week to earlier until views end or activity end
		while (idIndex >= 0 && activityIndex >= 0) {

			Integer id = cellIds.get(idIndex);
			DayActivity activity = userActivity.get(activityIndex);

			int color = ActivityColor.calculateColor(minColor, maxColor, maxActivity, activity.getCount());
			remoteViews.setInt(id, "setBackgroundColor", color);

			idIndex--;
			activityIndex--;
		}
	}

	private static void setWeeksVisibility(RemoteViews remoteViews, ActivityWidget widget) {
		List<Integer> weekViewIds = widget.getWeekViewIds();

		int visibleWeeksCount = widget.getVisibleWeeksCount();

		for (int week = 0; week < weekViewIds.size(); week++) {
			Integer weekId = weekViewIds.get(week);
			int visibility = week < visibleWeeksCount ? View.VISIBLE : View.GONE;
			remoteViews.setViewVisibility(weekId, visibility);
		}
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
