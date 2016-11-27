package github.activity;

import android.support.v7.app.ActionBarActivity;

import com.github.DayActivityFromServer;
import com.github.GitHubClient;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


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
				GitHubClient client = new GitHubClient();
				try {
					List<DayActivityFromServer> userActivity = client.getUserActivity("swapii");
					updateActivityView(userActivity);
				} catch (GitHubClient.PageParseException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@UiThread
	protected void updateActivityView(List<DayActivityFromServer> userActivity) {
	}

}
