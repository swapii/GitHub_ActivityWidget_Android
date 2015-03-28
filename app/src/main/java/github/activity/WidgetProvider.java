package github.activity;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RemoteViews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.dao.DayActivity;

/**
 * Created by asavinova on 26/12/14.
 */
public class WidgetProvider extends AppWidgetProvider {

	private static final Logger L = LoggerFactory.getLogger(WidgetProvider.class);

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int widgetId : appWidgetIds) {
			L.trace("Update widget {}", widgetId);
			Bundle options = appWidgetManager.getAppWidgetOptions(widgetId);
			updateWidget(context, appWidgetManager, widgetId, options);
		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		L.trace("Widget {} options changed", appWidgetId);
		updateWidget(context, appWidgetManager, appWidgetId, newOptions);
	}

	private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle options) {

		List<DayActivity> activityList = Dao_.getInstance_(context).getUserActivity("swapii");

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float density = metrics.density;

		AppWidgetProviderInfo providerInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);

		int maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);

		int width = (int) (maxWidth * density);
		if (width == 0 && providerInfo != null) {
			width = providerInfo.minWidth;
		}

		int maxHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

		int height = (int) (maxHeight * density);
		if (height == 0 && providerInfo != null) {
			height = providerInfo.minHeight;
		}

		Bitmap bitmap = createBitmap(width, height, activityList);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		remoteViews.setImageViewBitmap(R.id.image, bitmap);
		remoteViews.setViewVisibility(R.id.loading_view, View.GONE);
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	private Bitmap createBitmap(int width, int height, List<DayActivity> userActivity) {

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		ColorContainer minColor = new ColorContainer(Color.parseColor("#30FFFFFF"));
		ColorContainer maxColor = new ColorContainer(Color.parseColor("#FFFFFFFF"));

		int maxActivity = ActivityColorUtil.getMaxActivity(userActivity);

		double cellHeightRaw = (double) height / 7;
		int columnsCountRaw = (int) ((double) width / cellHeightRaw);

		double cellWidthRaw = (double) width / columnsCountRaw;
		double cellSizeRaw = Math.min(cellWidthRaw, cellHeightRaw);

		double squareVisibleSizeRaw = cellSizeRaw * 16 / (16 + 2);

		int squareVisibleSize = ((int) (squareVisibleSizeRaw / 4)) * 4;

		double divisionResult = cellSizeRaw / 18;
		int squarePadding = divisionResult < 1 ? 1 : (int) divisionResult;

		int cellSize = squarePadding * 2 + squareVisibleSize;

		int columnsCount = width / cellSize;
		int rowsCount = 7;

		int row = rowsCount - 1;
		int column = columnsCount - 1;

		int offsetX = (width - columnsCount * cellSize) / 2;
		int offsetY = (height - rowsCount * cellSize) / 2;

		for (DayActivity activity : userActivity) {

			int left = offsetX + cellSize * column + squarePadding;
			int right = left + squareVisibleSize;

			int top = offsetY + cellSize * row + squarePadding;
			int bottom = top + squareVisibleSize;


			int color = ActivityColorUtil.calculateColor(minColor, maxColor, maxActivity, activity);

			Paint paint = new Paint();
			paint.setColor(color);
			canvas.drawRect(left, top, right, bottom, paint);

			row--;

			if (row < 0) {
				row = rowsCount - 1;
				column--;
			}

			if (column < 0) {
				break;
			}
		}

		return bitmap;
	}

}
