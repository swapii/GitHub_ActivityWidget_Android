package github.activity.ui

import android.graphics.Color

data class ActivityColor(
	val alpha: Int,
	val h: Float,
	val s: Float,
	val v: Float
) {

	companion object {

		fun fromIntValue(color: Int): ActivityColor {
			val hsv = FloatArray(3)
			Color.colorToHSV(color, hsv)
			val alpha = Color.alpha(color)
			val h = hsv[0]
			val s = hsv[1]
			val v = hsv[2]
			return ActivityColor(alpha, h, s, v)
		}

		fun calculateColor(
			minColor: ActivityColor,
			maxColor: ActivityColor,
			maxActivity: Int,
			currentActivity: Int
		): Int {

            val coeff = currentActivity.toFloat() / maxActivity

            val a = (minColor.alpha + (maxColor.alpha - minColor.alpha) * coeff).toInt()
            val h = minColor.h + (maxColor.h - minColor.h) * coeff
            val s = minColor.s + (maxColor.s - minColor.s) * coeff
            val v = minColor.v + (maxColor.v - minColor.v) * coeff

            var color = Color.HSVToColor(floatArrayOf(h, s, v))
            color = Color.argb(
				a,
				Color.red(color),
				Color.green(color),
				Color.blue(color)
			)
            return color
        }

    }

}
