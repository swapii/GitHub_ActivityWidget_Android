package github.activity.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
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

		List<Integer> weekViewIds = Arrays.asList(

				R.id.week_00,
				R.id.week_01,
				R.id.week_02,
				R.id.week_03,
				R.id.week_04,
				R.id.week_05,
				R.id.week_06,
				R.id.week_07,
				R.id.week_08,
				R.id.week_09,

				R.id.week_10,
				R.id.week_11,
				R.id.week_12,
				R.id.week_13,
				R.id.week_14,
				R.id.week_15,
				R.id.week_16,
				R.id.week_17,
				R.id.week_18,
				R.id.week_19,

				R.id.week_20,
				R.id.week_21,
				R.id.week_22,
				R.id.week_23,
				R.id.week_24,
				R.id.week_25,
				R.id.week_26,
				R.id.week_27,
				R.id.week_28,
				R.id.week_29

		);

		Bundle widgetOptions = widgetManager.getAppWidgetOptions(widgetId);
		int width = widgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

		int defaultWidgetCellSize = 74;
		int gridCells = Math.round((float) width / defaultWidgetCellSize);
		width -= gridCells * 8;

		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();

		float density = metrics.density;
		float cellSize = resources.getDimension(R.dimen.widget_grid_cell_size) / density;
		float cellMargin = resources.getDimension(R.dimen.widget_grid_cell_margin) / density;
		float cellSumWidth = cellSize + cellMargin;

		int visibleWeeks = (int) (width / cellSumWidth);

		for (int week = 0; week < weekViewIds.size(); week++) {
			Integer weekId = weekViewIds.get(week);
			int visibility = week < visibleWeeks ? View.VISIBLE : View.GONE;
			remoteViews.setViewVisibility(weekId, visibility);
		}

		List<Integer> cellIds = Arrays.asList(

				R.id.cell_00_0, R.id.cell_00_1, R.id.cell_00_2, R.id.cell_00_3, R.id.cell_00_4, R.id.cell_00_5, R.id.cell_00_6,
				R.id.cell_01_0, R.id.cell_01_1, R.id.cell_01_2, R.id.cell_01_3, R.id.cell_01_4, R.id.cell_01_5, R.id.cell_01_6,
				R.id.cell_02_0, R.id.cell_02_1, R.id.cell_02_2, R.id.cell_02_3, R.id.cell_02_4, R.id.cell_02_5, R.id.cell_02_6,
				R.id.cell_03_0, R.id.cell_03_1, R.id.cell_03_2, R.id.cell_03_3, R.id.cell_03_4, R.id.cell_03_5, R.id.cell_03_6,
				R.id.cell_04_0, R.id.cell_04_1, R.id.cell_04_2, R.id.cell_04_3, R.id.cell_04_4, R.id.cell_04_5, R.id.cell_04_6,
				R.id.cell_05_0, R.id.cell_05_1, R.id.cell_05_2, R.id.cell_05_3, R.id.cell_05_4, R.id.cell_05_5, R.id.cell_05_6,
				R.id.cell_06_0, R.id.cell_06_1, R.id.cell_06_2, R.id.cell_06_3, R.id.cell_06_4, R.id.cell_06_5, R.id.cell_06_6,
				R.id.cell_07_0, R.id.cell_07_1, R.id.cell_07_2, R.id.cell_07_3, R.id.cell_07_4, R.id.cell_07_5, R.id.cell_07_6,
				R.id.cell_08_0, R.id.cell_08_1, R.id.cell_08_2, R.id.cell_08_3, R.id.cell_08_4, R.id.cell_08_5, R.id.cell_08_6,
				R.id.cell_09_0, R.id.cell_09_1, R.id.cell_09_2, R.id.cell_09_3, R.id.cell_09_4, R.id.cell_09_5, R.id.cell_09_6,

				R.id.cell_10_0, R.id.cell_10_1, R.id.cell_10_2, R.id.cell_10_3, R.id.cell_10_4, R.id.cell_10_5, R.id.cell_10_6,
				R.id.cell_11_0, R.id.cell_11_1, R.id.cell_11_2, R.id.cell_11_3, R.id.cell_11_4, R.id.cell_11_5, R.id.cell_11_6,
				R.id.cell_12_0, R.id.cell_12_1, R.id.cell_12_2, R.id.cell_12_3, R.id.cell_12_4, R.id.cell_12_5, R.id.cell_12_6,
				R.id.cell_13_0, R.id.cell_13_1, R.id.cell_13_2, R.id.cell_13_3, R.id.cell_13_4, R.id.cell_13_5, R.id.cell_13_6,
				R.id.cell_14_0, R.id.cell_14_1, R.id.cell_14_2, R.id.cell_14_3, R.id.cell_14_4, R.id.cell_14_5, R.id.cell_14_6,
				R.id.cell_15_0, R.id.cell_15_1, R.id.cell_15_2, R.id.cell_15_3, R.id.cell_15_4, R.id.cell_15_5, R.id.cell_15_6,
				R.id.cell_16_0, R.id.cell_16_1, R.id.cell_16_2, R.id.cell_16_3, R.id.cell_16_4, R.id.cell_16_5, R.id.cell_16_6,
				R.id.cell_17_0, R.id.cell_17_1, R.id.cell_17_2, R.id.cell_17_3, R.id.cell_17_4, R.id.cell_17_5, R.id.cell_17_6,
				R.id.cell_18_0, R.id.cell_18_1, R.id.cell_18_2, R.id.cell_18_3, R.id.cell_18_4, R.id.cell_18_5, R.id.cell_18_6,
				R.id.cell_19_0, R.id.cell_19_1, R.id.cell_19_2, R.id.cell_19_3, R.id.cell_19_4, R.id.cell_19_5, R.id.cell_19_6,

				R.id.cell_20_0, R.id.cell_20_1, R.id.cell_20_2, R.id.cell_20_3, R.id.cell_20_4, R.id.cell_20_5, R.id.cell_20_6,
				R.id.cell_21_0, R.id.cell_21_1, R.id.cell_21_2, R.id.cell_21_3, R.id.cell_21_4, R.id.cell_21_5, R.id.cell_21_6,
				R.id.cell_22_0, R.id.cell_22_1, R.id.cell_22_2, R.id.cell_22_3, R.id.cell_22_4, R.id.cell_22_5, R.id.cell_22_6,
				R.id.cell_23_0, R.id.cell_23_1, R.id.cell_23_2, R.id.cell_23_3, R.id.cell_23_4, R.id.cell_23_5, R.id.cell_23_6,
				R.id.cell_24_0, R.id.cell_24_1, R.id.cell_24_2, R.id.cell_24_3, R.id.cell_24_4, R.id.cell_24_5, R.id.cell_24_6,
				R.id.cell_25_0, R.id.cell_25_1, R.id.cell_25_2, R.id.cell_25_3, R.id.cell_25_4, R.id.cell_25_5, R.id.cell_25_6,
				R.id.cell_26_0, R.id.cell_26_1, R.id.cell_26_2, R.id.cell_26_3, R.id.cell_26_4, R.id.cell_26_5, R.id.cell_26_6,
				R.id.cell_27_0, R.id.cell_27_1, R.id.cell_27_2, R.id.cell_27_3, R.id.cell_27_4, R.id.cell_27_5, R.id.cell_27_6,
				R.id.cell_28_0, R.id.cell_28_1, R.id.cell_28_2, R.id.cell_28_3, R.id.cell_28_4, R.id.cell_28_5, R.id.cell_28_6,
				R.id.cell_29_0, R.id.cell_29_1, R.id.cell_29_2, R.id.cell_29_3, R.id.cell_29_4, R.id.cell_29_5, R.id.cell_29_6

		);
		Collections.reverse(cellIds);

		int idIndex = cellIds.size() - 1;
		int activityIndex = userActivity.size() - 1;

		while (idIndex >= 0 && activityIndex >= 0) {

			Integer id = cellIds.get(idIndex);
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
