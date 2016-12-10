package github.activity.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import github.activity.R;

/**
 * Wrapper for widget with useful methods.
 *
 * @author Pavel Savinov (swapii@gmail.com)
 */
public class ActivityWidget {

	private static final List<Integer> WEEK_VIEW_IDS = Arrays.asList(

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

	private static final List<Integer> CELL_VIEW_IDS_ASC = Collections.unmodifiableList(Arrays.asList(

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

	));

	private static final List<Integer> CELL_VIEW_IDS_DESC;

	static {
		List<Integer> cellViewIdsAsc = new ArrayList<>(CELL_VIEW_IDS_ASC);
		Collections.reverse(cellViewIdsAsc);
		CELL_VIEW_IDS_DESC = Collections.unmodifiableList(cellViewIdsAsc);
	}

	private Context context;
	private final int widgetId;
	private final AppWidgetManager widgetManager;

	public ActivityWidget(Context context, int widgetId) {
		this.context = context;
		this.widgetId = widgetId;
		widgetManager = AppWidgetManager.getInstance(context);
	}

	int getWidth() {
		Bundle widgetOptions = widgetManager.getAppWidgetOptions(widgetId);
		return widgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
	}

	public List<Integer> getWeekViewIds() {
		return WEEK_VIEW_IDS;
	}

	public List<Integer> getCellViewIdsDesc() {
		return CELL_VIEW_IDS_DESC;
	}

	public int getVisibleWeeksCount() {
		int width = getWidth();

		int defaultWidgetCellSize = 74;
		int gridCells = Math.round((float) width / defaultWidgetCellSize);
		width -= gridCells * 8;

		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();

		float density = metrics.density;
		float cellSize = resources.getDimension(R.dimen.widget_grid_cell_size) / density;
		float cellMargin = resources.getDimension(R.dimen.widget_grid_cell_margin) / density;
		float cellSumWidth = cellSize + cellMargin;

		return (int) (width / cellSumWidth);
	}

	public String getUsername() {
		//TODO Get username from options bundle
		return "swapii";
	}

}
