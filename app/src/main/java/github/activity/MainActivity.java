package github.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import github.activity.client.DayActivity;
import github.activity.client.GitHubClient;


public class MainActivity extends ActionBarActivity {

    private static final Logger L = LoggerFactory.getLogger(MainActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                L.error("Start thread");
                GitHubClient gitHubClient = new GitHubClient();
                List<DayActivity> userActivity = gitHubClient.getUserActivity("swapii");
                for (DayActivity activity : userActivity) {
                    String message = String.format("%s %s", activity.getDate(), activity.getActivityCount());
                    L.error(message);
                }

            }
        }).start();

    }

}
