package github.activity.ui;

import android.app.Activity;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import github.activity.R;
import github.activity.ui.widget.ActivityWidget;

/**
 * Created by pavel on 24/01/2017.
 */
@EActivity(R.layout.widget_preferences)
public class PreferencesActivity extends Activity {

	@ViewById(R.id.widget_id) TextView widgetIdView;
	@ViewById(R.id.username) EditText usernameEditText;

	@Extra int widgetId;

	private ActivityWidget widget;

	@AfterInject
	void afterInject() {
		widget = new ActivityWidget(this, widgetId);
	}

	@AfterViews
	void afterViews() {
		widgetIdView.setText(String.valueOf(widgetId));
	}

	@Override
	protected void onResume() {
		super.onResume();
		usernameEditText.setText(widget.getUsername());
	}

	@AfterTextChange(R.id.username)
	void usernameChanged(TextView textView, Editable text) {
		widget.setUsername(text.toString().trim());
	}

}
