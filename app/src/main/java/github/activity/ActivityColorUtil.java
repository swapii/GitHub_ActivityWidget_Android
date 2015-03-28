package github.activity;

import android.graphics.Color;

import java.util.List;

import github.activity.client.DayActivity;

/**
 * Created by pavel on 28/03/15.
 */
public class ActivityColorUtil {
	static int calculateColor(ColorContainer minColor, ColorContainer maxColor, int maxActivity, DayActivity activity) {

		float coeff = (float) activity.getActivityCount() / maxActivity;

		int a = (int) (minColor.getAlpha() + (maxColor.getAlpha() - minColor.getAlpha()) * coeff);
		float h = minColor.getH() + (maxColor.getH() - minColor.getH()) * coeff;
		float s = minColor.getS() + (maxColor.getS() - minColor.getS()) * coeff;
		float v = minColor.getV() + (maxColor.getV() - minColor.getV()) * coeff;

		int color = Color.HSVToColor(new float[]{h, s, v});
		color = Color.argb(a, Color.red(color), Color.green(color), Color.blue(color));
		return color;
	}

	static int getMaxActivity(List<DayActivity> userActivity) {
		int maxActivity = 0;
		for (DayActivity activity : userActivity) {
			maxActivity = Math.max(maxActivity, activity.getActivityCount());
		}
		return maxActivity;
	}
}
