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
import android.widget.RemoteViews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

/**
 * Created by asavinova on 26/12/14.
 */
public class GitHubWidgetProvider extends AppWidgetProvider {

	private static final Logger L = LoggerFactory.getLogger(GitHubWidgetProvider.class.getSimpleName());

	private int margin;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		for (int widgetId : appWidgetIds) {
			Bundle options = appWidgetManager.getAppWidgetOptions(widgetId);
			updateWidget(context, appWidgetManager, widgetId, options);
		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
		updateWidget(context, appWidgetManager, appWidgetId, newOptions);
	}

	private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		margin = (int) displayMetrics.density * 1;

		AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(appWidgetId);

		int width = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH) - newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
		if (width == 0 && info != null) {
			width = info.minWidth;
		}

		int height = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT) - newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
		if (height == 0 && info != null) {
			height = info.minHeight;
		}


		RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.init_widget);
		String msg = String.format(Locale.getDefault(),
						"[%d x %d]", width, height);

		updateViews.setTextViewText(R.id.size, msg);
		updateViews.setImageViewBitmap(R.id.image, createBitmap(width, height));

		appWidgetManager.updateAppWidget(appWidgetId, updateViews);
	}

	private Bitmap createBitmap(int width, int height) {
		double cellHeight = (double) (height - margin * 7 * 2) / 7;
		int columnsCount = (int) (width / cellHeight);
		double cellWidth = (width - margin * columnsCount * 2) / columnsCount;
		int cellSize = (int) Math.min(cellWidth, cellHeight);

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);

		int[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.YELLOW, Color.WHITE};

		int rowCount = 7;
		int row = rowCount - 1;
		int column = columnsCount - 1;
		for (int i = 0; i < 366; i++) {

			int left = margin + (columnsCount - column + 1) * cellSize + (columnsCount - column + 1) * margin;
			int top = margin + (rowCount - row + 1) * cellSize + (rowCount - row + 1) * margin;
			int right = left - cellSize;
			int bottom = top + cellSize;

			L.info(String.format(Locale.getDefault(),
					"[%d, %d, %d, %d]", left, top, right, bottom));

			Paint p = new Paint();
			p.setColor(colors[i % 5]);
			canvas.drawRect(left, top, right, bottom, p);

			row--;

			if (row < 0) {
				row = rowCount - 1;
				column--;
			}

			if (column < 0) {
				return bitmap;
			}
		}

		return bitmap;
	}

}
