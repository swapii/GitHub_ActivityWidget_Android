package github.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

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

		ColorContainer minColor = new ColorContainer(Color.parseColor("#30FFFFFF"));
		ColorContainer maxColor = new ColorContainer(Color.parseColor("#FFFFFFFF"));

		int maxActivity = ActivityColorUtil.getMaxActivity(userActivity);

		for (DayActivity activity : userActivity) {

			int color = ActivityColorUtil.calculateColor(minColor, maxColor, maxActivity, activity);

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

	public void update(Intent intent) {
		Toast.makeText(getContext(), "WOW!", Toast.LENGTH_LONG).show();
	}

}
