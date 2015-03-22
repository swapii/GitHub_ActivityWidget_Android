package github.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

/**
 * Created by Pavel on 26.12.2014.
 */
@RemoteViews.RemoteView
public class TestView extends View {

	public TestView(Context context) {
		super(context);
	}

	public TestView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

}
