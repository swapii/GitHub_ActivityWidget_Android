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
