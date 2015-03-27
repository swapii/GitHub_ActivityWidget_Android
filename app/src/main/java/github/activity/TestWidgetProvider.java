package github.activity;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.RemoteViews;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pavel on 23/03/15.
 */
public class TestWidgetProvider extends AppWidgetProvider {

	private static final Logger L = LoggerFactory.getLogger(TestWidgetProvider.class);

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		L.trace("Update widgets");

		for (int appWidgetId : appWidgetIds) {
			updateWidget(context, appWidgetManager, appWidgetId);
		}
	}

	@Override
	public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {

		L.trace("Widget {} options changed", appWidgetId);

		updateWidget(context, appWidgetManager, appWidgetId);
	}

	private void updateWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();

		Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
		int minWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
		int maxWidth = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
		int minHeigh = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
		int maxHeigh = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.test_widget);

		int widthPx = (int) (maxWidth * metrics.density);
		int heightPx = (int) (maxHeigh * metrics.density);
		remoteViews.setImageViewBitmap(R.id.image_view, getBitmap(widthPx, heightPx));

		remoteViews.setTextViewText(R.id.text_view, String.format("[%s-%s]x[%s-%s]", minWidth, maxWidth, minHeigh,maxHeigh));

		appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
	}

	private Bitmap getBitmap(int width, int height) {

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);

		Paint paint = new Paint();
		paint.setColor(Color.parseColor("#ff0000"));

		canvas.drawRect(0, 0, 10, 10, paint);
		canvas.drawRect(width - 10, 0, width, 10, paint);
		canvas.drawRect(width - 10, height - 10, width, height, paint);
		canvas.drawRect(0, height - 10, 10, height, paint);

		return bitmap;
	}

}
