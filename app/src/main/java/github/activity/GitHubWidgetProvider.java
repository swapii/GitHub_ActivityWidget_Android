package github.activity;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.widget.RemoteViews;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import github.activity.client.DayActivity;
import github.activity.client.GitHubClient;

/**
 * Created by Pavel on 25.12.2014.
 */
public class GitHubWidgetProvider extends AppWidgetProvider {

	static SparseArray<RemoteViews> remoteViewsArray = new SparseArray<>();
	static SparseArray<ActivityView> viewsArray = new SparseArray<>();
	static SparseArray<Pair<Integer, Integer>> sizesArray = new SparseArray<>();

	private List<DayActivity> userActivity;

	private ScheduledExecutorService executorService;

	{
		executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					userActivity = new GitHubClient().getUserActivity("swapii");
					Log.w("", "Updated");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 5, TimeUnit.SECONDS);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		for (int widgetId : appWidgetIds) {

			RemoteViews views = remoteViewsArray.get(widgetId);

			if (views == null) {
				views = new RemoteViews(context.getApplicationInfo().packageName, R.layout.init_widget);
				remoteViewsArray.append(widgetId, views);
			}

			ActivityView activityView = viewsArray.get(widgetId);
			if (activityView == null) {
				activityView = new ActivityView_(context);
				viewsArray.append(widgetId, activityView);
			}

			Pair<Integer, Integer> size = sizesArray.get(widgetId);

			if (size == null) {
				return;
			}

			if (userActivity != null) {
				activityView.update(userActivity);
			}

			activityView.measure(size.first, size.second);
			activityView.layout(0, 0, size.first, size.second);
			activityView.setDrawingCacheEnabled(true);

			Bitmap bitmap = activityView.getDrawingCache();
			views.setBitmap(R.id.image_view, "setImageBitmap", bitmap);

			appWidgetManager.updateAppWidget(widgetId, views);

		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
		int width = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
		int height = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
		sizesArray.append(appWidgetId, new Pair<>(width, height));
	}

}
