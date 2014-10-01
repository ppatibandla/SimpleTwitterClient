package com.codepath.apps.basictwitter;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.text.format.DateUtils;

public class Utils {
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public static String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat,
				Locale.ENGLISH);
		sf.setLenient(true);

		String relativeTime = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			long diff = System.currentTimeMillis() - dateMillis;
			if (diff < 0) {
				diff = 0;
			}
			if (diff > DateUtils.WEEK_IN_MILLIS) {
				relativeTime = String.valueOf(((int) diff / DateUtils.WEEK_IN_MILLIS)) + " w";
			} else if (diff > DateUtils.DAY_IN_MILLIS) {
				relativeTime = String.valueOf(((int) diff / DateUtils.DAY_IN_MILLIS)) + " d";
			} else if (diff > DateUtils.HOUR_IN_MILLIS) {
				relativeTime = String.valueOf(((int) diff / DateUtils.HOUR_IN_MILLIS)) + " h";
			} else if (diff > DateUtils.MINUTE_IN_MILLIS) {
				relativeTime = String.valueOf(((int) diff / DateUtils.MINUTE_IN_MILLIS)) + " m";
			} else {
				relativeTime = String.valueOf(((int) diff / DateUtils.SECOND_IN_MILLIS)) + " s";
			}
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return relativeTime;
	}
}
