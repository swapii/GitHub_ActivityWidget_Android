package github.activity.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import github.activity.R;
import github.activity.ui.widget.ActivityWidget;

public class PreferencesActivity extends AppCompatActivity {

	private static final String EXTRA_WIDGET_ID = "widget_id";

	private EditText usernameEditText;

	private ActivityWidget widget;

	public static Intent createIntent(Context context, int widgetId) {
		Intent intent = new Intent(context, PreferencesActivity.class);
		intent.putExtra(EXTRA_WIDGET_ID, widgetId);
		return intent;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget_preferences);

		int widgetId = getIntent().getIntExtra(EXTRA_WIDGET_ID, -1);

		widget = new ActivityWidget(this, widgetId);

		Toolbar toolbarView = findViewById(R.id.toolbar);
		toolbarView.setTitle(R.string.preferences_title);
		toolbarView.setSubtitle(R.string.preferences_subtitle);

		usernameEditText = findViewById(R.id.username);
		usernameEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}

			@Override
			public void afterTextChanged(Editable text) {
				widget.setUsername(text.toString().trim());
			}

		});

	}

	@Override
	protected void onResume() {
		super.onResume();
		usernameEditText.setText(widget.getUsername());
	}

}
