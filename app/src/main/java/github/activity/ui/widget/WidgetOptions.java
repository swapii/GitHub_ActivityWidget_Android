package github.activity.ui.widget;

import android.os.Bundle;

/**
 * @author Pavel Savinov (swapii@gmail.com)
 */

public class WidgetOptions {

	private static final String USERNAME_KEY = "username";

	String username;

	public WidgetOptions(Bundle bundle) {
		username = bundle.getString(USERNAME_KEY);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Bundle toBundle() {
		Bundle bundle = new Bundle();
		bundle.putString(USERNAME_KEY, username);
		return bundle;
	}

}
