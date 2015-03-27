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

/**
 * Created by asavinova on 26/12/14.
 */
public class WidgetProvider extends AppWidgetProvider {

	private static final Logger L = LoggerFactory.getLogger(WidgetProvider.class);

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

	private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle options) {

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

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
		remoteViews.setImageViewBitmap(R.id.image, createBitmap(width, height));
		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	private Bitmap createBitmap(int width, int height) {

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		Paint borderPaint = new Paint();
		borderPaint.setColor(Color.MAGENTA);
		borderPaint.setStrokeWidth(8);
		borderPaint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(0, 0, width, height, borderPaint);

		int[] colors = {
				Color.parseColor("#ff0000"),
				Color.parseColor("#ff9500"),
				Color.parseColor("#fffb00"),
				Color.parseColor("#00f900"),
				Color.parseColor("#00fdff"),
				Color.parseColor("#0433ff"),
				Color.parseColor("#942192")
		};


		double cellHeightRaw = (double) height / 7;
		int columnsCountRaw = (int) ((double) width / cellHeightRaw);

		double cellWidthRaw = (double) width / columnsCountRaw;
		double cellSizeRaw = Math.min(cellWidthRaw, cellHeightRaw);

		L.trace("{} {} {} {}", cellHeightRaw, cellWidthRaw, columnsCountRaw, cellSizeRaw);

		double squareVisibleSizeRaw = cellSizeRaw * 16 / (16 + 2);
		L.trace("Square size raw " + squareVisibleSizeRaw);

		int squareVisibleSize = ((int) (squareVisibleSizeRaw / 4)) * 4;
		L.trace("Square size 4 = " + squareVisibleSize);

		double divisionResult = cellSizeRaw / 18;
		int squarePadding = divisionResult < 1 ? 1 : (int) divisionResult;

		int cellSize = squarePadding * 2 + squareVisibleSize;

		int columnsCount = width / cellSize;
		int rowsCount = 7;

		int row = rowsCount - 1;
		int column = columnsCount - 1;

		int offsetX = (width - columnsCount * cellSize) / 2;
		int offsetY = (height - rowsCount * cellSize) / 2;

		for (int i = 0; i < 366; i++) {

			int left = offsetX + cellSize * column + squarePadding;
			int right = left + squareVisibleSize;

			int top = offsetY + cellSize * row + squarePadding;
			int bottom = top + squareVisibleSize;

			Paint paint = new Paint();
			paint.setColor(colors[i % 7]);
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
