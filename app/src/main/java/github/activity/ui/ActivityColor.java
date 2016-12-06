package github.activity.ui;

import android.graphics.Color;

/**
* Created by pavel on 28/03/15.
*/
public class ActivityColor {

	int alpha;
	float h, s, v;

	public ActivityColor(int color) {
		alpha = Color.alpha(color);
		float[] hsv = new float[3];
		Color.colorToHSV(color, hsv);
		h = hsv[0];
		s = hsv[1];
		v = hsv[2];
	}

	public static int calculateColor(ActivityColor minColor, ActivityColor maxColor, int maxActivity, int currentActivity) {

		float coeff = (float) currentActivity / maxActivity;

		int a = (int) (minColor.getAlpha() + (maxColor.getAlpha() - minColor.getAlpha()) * coeff);
		float h = minColor.getH() + (maxColor.getH() - minColor.getH()) * coeff;
		float s = minColor.getS() + (maxColor.getS() - minColor.getS()) * coeff;
		float v = minColor.getV() + (maxColor.getV() - minColor.getV()) * coeff;

		int color = Color.HSVToColor(new float[]{h, s, v});
		color = Color.argb(a, Color.red(color), Color.green(color), Color.blue(color));
		return color;
	}

	public int getAlpha() {
		return alpha;
	}

	public float getH() {
		return h;
	}

	public float getS() {
		return s;
	}

	public float getV() {
		return v;
	}

}
