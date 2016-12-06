package com.github;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by swap_i on 21/12/14.
 */
public class GitHubClient {

    private static final Logger L = LoggerFactory.getLogger(GitHubClient.class);
    private static final Pattern CELL_PATTERN = Pattern.compile("<rect class=\"day\" .+? data-count=\"(\\d+?)\" data-date=\"(\\d{4}-\\d{2}-\\d{2})\"/>", Pattern.DOTALL);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public List<DayActivityFromServer> getUserActivity(String username) throws PageParseException {

        try {
            URLConnection connection = new URL("https://github.com/" + username).openConnection();

            connection.connect();

            StringBuilder builder = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            try {
                int readed;
                char[] buffer = new char[1024];
                while ((readed = reader.read(buffer)) != -1) {
                    builder.append(buffer, 0, readed);
                }
            } finally {
                reader.close();
            }

            String resultStream = builder.toString();
            Matcher matcher = CELL_PATTERN.matcher(resultStream);

            List<DayActivityFromServer> list = new ArrayList<>();
            while (matcher.find()) {

                int count = Integer.parseInt(matcher.group(1));
                Date date = DATE_FORMAT.parse(matcher.group(2));

                DayActivityFromServer activity = new DayActivityFromServer();
                activity.setDate(date);
                activity.setActivityCount(count);
                list.add(activity);
            }

			if (list.isEmpty()) {
				throw new PageParseException("Can't find any activity on page");
			}

            return list;

        } catch (IOException e) {
            L.error("Error", e);

        } catch (ParseException e) {
            L.error("Error", e);
        }

        return new ArrayList<>();
    }

	public class PageParseException extends Exception {

		public PageParseException(String s) {
			super(s);
		}

	}

}