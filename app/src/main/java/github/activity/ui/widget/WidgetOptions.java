package github.activity.ui.widget;

import android.os.Bundle;

/**
 * @author Pavel Savinov (swapii@gmail.com)
 */
public class WidgetOptions {

	private static final String USERNAME_KEY = "username";

	private String username;
	private Bundle bundle;

	public WidgetOptions(Bundle bundle) {
		username = bundle.getString(USERNAME_KEY, "JakeWharton");
		this.bundle = bundle;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Bundle toBundle() {
		bundle.putString(USERNAME_KEY, username);
		return bundle;
	}

}
