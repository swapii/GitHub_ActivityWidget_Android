package github.activity;

import android.app.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by pavel on 22/03/15.
 */
public class App extends Application {

	private static final Logger L = LoggerFactory.getLogger(App.class);

	@Override
	public void onCreate() {
		super.onCreate();
		L.trace("App create");
	}

}
