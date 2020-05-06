package github.activity.ui;

import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import github.activity.R;
import github.activity.ui.widget.ActivityWidget;

@EActivity(R.layout.widget_preferences)
public class PreferencesActivity extends AppCompatActivity {

	@ViewById(R.id.toolbar) Toolbar toolbarView;
	@ViewById(R.id.username) EditText usernameEditText;

	@Extra int widgetId;

	private ActivityWidget widget;

	@AfterInject
	void afterInject() {
		widget = new ActivityWidget(this, widgetId);
	}

	@AfterViews
	void afterViews() {
		toolbarView.setTitle(R.string.preferences_title);
		toolbarView.setSubtitle(R.string.preferences_subtitle);
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
