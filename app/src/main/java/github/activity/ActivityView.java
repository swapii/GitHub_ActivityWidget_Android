package github.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import github.activity.client.DayActivity;

/**
 * Created by Pavel on 23.12.2014.
 */
@EViewGroup
public class ActivityView extends GridLayout {

	private static final Logger L = LoggerFactory.getLogger(ActivityView.class.getSimpleName());
	private int cellSize;
	private int margin;

	public ActivityView(Context context) {
		super(context);
	}

	public ActivityView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ActivityView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@AfterViews
	void afterViews() {

		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		margin = (int) displayMetrics.density * 1;

		setOrientation(VERTICAL);

		setRowCount(7);
		addOnLayoutChangeListener(new OnLayoutChangeListener() {
			@Override
			public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

				int height = v.getHeight();
				int width = v.getWidth();

				double cellHeight = (double) (height - margin * 7 * 2) / 7;

				int columnsCount = (int) (width / cellHeight);
				setColumnCount(columnsCount);

				double cellWidth = (width - margin * columnsCount * 2) / columnsCount;

				cellSize = (int) Math.min(cellWidth, cellHeight);
			}
		});

	}

	public void update(List<DayActivity> userActivity) {

		removeAllViews();

		Collections.reverse(userActivity);

		DayActivity dayActivity = userActivity.get(0);
		Calendar instance = Calendar.getInstance();
		instance.setTime(dayActivity.getDate());

		int column = getColumnCount() - 1;
		int row = getRowCount() - 1;

		int i1 = instance.get(Calendar.DAY_OF_WEEK);
		for (int i = getRowCount(); i > i1; i--) {

			LayoutParams params = new LayoutParams(GridLayout.spec(row), GridLayout.spec(column));
			params.width = cellSize;
			params.height = cellSize;
			params.setMargins(margin, margin, margin, margin);

			View view = new View(getContext());
			view.setLayoutParams(params);
			view.setBackgroundColor(getResources().getColor(android.R.color.transparent));

			addView(view);

			row--;

			if (row < 0) {
				row = getRowCount() - 1;
				column--;
			}

			if (column < 0) {
				return;
			}
		}

		int minColor = Color.parseColor("#30FFFFFF");
		int maxColor = Color.parseColor("#FFFFFFFF");

		float[] minHsv = new float[3];
		float[] maxHsv = new float[3];

		int minAlpha = Color.alpha(minColor);
		int maxAlpha = Color.alpha(maxColor);

		Color.colorToHSV(minColor, minHsv);
		Color.colorToHSV(maxColor, maxHsv);

		int maxActivity = 0;
		for (DayActivity activity : userActivity) {
			maxActivity = Math.max(maxActivity, activity.getActivityCount());
		}

		for (DayActivity activity : userActivity) {

			float coeff = (float) activity.getActivityCount() / maxActivity;

			int a = (int) (minAlpha + (maxAlpha - minAlpha) * coeff);
			float h = minHsv[0] + (maxHsv[0] - minHsv[0]) * coeff;
			float s = minHsv[1] + (maxHsv[1] - minHsv[1]) * coeff;
			float v = minHsv[2] + (maxHsv[2] - minHsv[2]) * coeff;

			float[] colorHsv = new float[]{h, s, v};

			int color = Color.HSVToColor(colorHsv);
			color = Color.argb(a, Color.red(color), Color.green(color), Color.blue(color));

			LayoutParams params = new LayoutParams(GridLayout.spec(row), GridLayout.spec(column));
			params.width = cellSize;
			params.height = cellSize;
			params.setMargins(margin, margin, margin, margin);

			TextView view = new TextView(getContext());
			view.setLayoutParams(params);
			view.setBackgroundColor(color);
			addView(view);

			row--;

			if (row < 0) {
				row = getRowCount() - 1;
				column--;
			}

			if (column < 0) {
				return;
			}
		}

	}

}
