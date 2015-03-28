package github.activity.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by asavinova on 28/03/15.
 */
public class BootCompletedReceiver extends BroadcastReceiver {

	private static final Logger L = LoggerFactory.getLogger(BootCompletedReceiver.class);

	@Override
	public void onReceive(Context context, Intent intent) {
		L.trace("Received action " + intent.getAction());
	}

}
