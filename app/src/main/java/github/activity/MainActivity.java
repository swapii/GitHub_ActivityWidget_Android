package github.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.client.DayActivity;
import github.activity.client.GitHubClient;

@EActivity(R.layout.main)
public class MainActivity extends ActionBarActivity {

    private static final Logger L = LoggerFactory.getLogger(MainActivity.class);

	@ViewById ActivityView activityView;

	@Override
	protected void onResume() {
		super.onResume();

		new Thread(new Runnable() {
			@Override
			public void run() {
				L.error("Start thread");
				GitHubClient gitHubClient = new GitHubClient();
				List<DayActivity> userActivity = gitHubClient.getUserActivity("swapii");
				updateActivityView(userActivity);
			}
		}).start();
	}

	@UiThread
	protected void updateActivityView(List<DayActivity> userActivity) {
		activityView.update(userActivity);
	}

}
