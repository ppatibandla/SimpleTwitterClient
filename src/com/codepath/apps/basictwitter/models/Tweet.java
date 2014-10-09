package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Tweet {
	private String body;
	private long uid;
	private String cretedAt;
	private User user;
	private boolean retweeted;
	
	public static Tweet fromJson(JSONObject json) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = json.getString("text");
			tweet.uid = json.getLong("id");
			tweet.cretedAt = json.getString("created_at");
			tweet.retweeted = json.getBoolean("retweeted");
			tweet.user = User.fromJson(json.getJSONObject("user"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public String getCretedAt() {
		return cretedAt;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(json.length());
		for (int i=0; i< json.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = json.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = fromJson(tweetJson);
			if(tweet != null) {
				tweets.add(tweet);
			}
			
			
		}
		// TODO Auto-generated method stub
		return tweets;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return user.getName() + "-" + body;
	}

}
