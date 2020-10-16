package com.github;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author Pavel Savinov (swapii@gmail.com)
 */
public class ActivityTest {

	private GitHubClient client;

	@Before
	public void before() {
		client = new GitHubClient();
	}

	@Test
	public void jakeWartonIsNotLazy() throws PageParseException {
		List<OneDayActivityFromServer> activityList = client.getUserActivity("JakeWharton");
		Assert.assertFalse(activityList.isEmpty());
		int sum = 0;
		for (OneDayActivityFromServer activity : activityList) {
			sum += activity.getActivityCount();
		}
		double averageActivity = (double) sum / activityList.size();
		Assert.assertTrue(averageActivity > 8);
	}

}
