package github.activity.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

		List<DayActivity> userActivity = Storage_.getInstance_(context).getUserActivity("swapii");

		if (userActivity.size() < 365) {
			return;
		}

		ActivityColor minColor = new ActivityColor(Color.parseColor("#30FFFFFF"));
		ActivityColor maxColor = new ActivityColor(Color.parseColor("#FFFFFFFF"));

		int maxActivity = findMaxActivity(userActivity);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);

		int index = Math.max(userActivity.size() - 7, 0);

		remoteViews.setInt(R.id.cell_0_0, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));
		remoteViews.setInt(R.id.cell_0_1, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));
		remoteViews.setInt(R.id.cell_0_2, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));
		remoteViews.setInt(R.id.cell_0_3, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));
		remoteViews.setInt(R.id.cell_0_4, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));
		remoteViews.setInt(R.id.cell_0_5, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));
		remoteViews.setInt(R.id.cell_0_6, "setBackgroundColor", ActivityColor.calculateColor(minColor, maxColor, maxActivity, userActivity.get(index++).getCount()));

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
