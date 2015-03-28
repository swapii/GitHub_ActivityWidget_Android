package github.activity;

import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.client.DayActivityFromServer;
import github.activity.client.GitHubClient;

@EActivity(R.layout.main)
public class MainActivity extends ActionBarActivity {

    private static final Logger L = LoggerFactory.getLogger(MainActivity.class);

	@Override
	protected void onResume() {
		super.onResume();

		new Thread(new Runnable() {
			@Override
			public void run() {
				L.error("Start thread");
				GitHubClient gitHubClient = new GitHubClient();
				List<DayActivityFromServer> userActivity = gitHubClient.getUserActivity("swapii");
				updateActivityView(userActivity);
			}
		}).start();
	}

	@UiThread
	protected void updateActivityView(List<DayActivityFromServer> userActivity) {
	}

}
